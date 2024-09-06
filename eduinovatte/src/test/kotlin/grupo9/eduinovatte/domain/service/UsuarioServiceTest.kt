package grupo9.eduinovatte.domain.service

import grupo9.eduinovatte.application.dto.response.UsuarioResponse
import grupo9.eduinovatte.domain.model.entity.NivelAcesso
import grupo9.eduinovatte.domain.model.entity.Situacao
import grupo9.eduinovatte.domain.service.impl.NivelAcessoServiceImpl
import grupo9.eduinovatte.domain.service.impl.UsuarioServiceImpl
import grupo9.eduinovatte.model.UsuarioBuilder
import grupo9.eduinovatte.model.enums.NivelAcessoNome
import grupo9.eduinovatte.model.enums.SituacaoNome
import grupo9.eduinovatte.service.UsuarioRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.web.server.ResponseStatusException
import java.util.*

class UsuarioServiceImplTest {

    lateinit var usuarioRepository: UsuarioRepository
    lateinit var nivelAcessoService: NivelAcessoServiceImpl
    lateinit var situacaoService: SituacaoService
    lateinit var horarioProfessorService: HorarioProfessorService
    lateinit var service: UsuarioService

    @BeforeEach
    fun iniciar() {
        usuarioRepository = mock(UsuarioRepository::class.java)
        nivelAcessoService = mock(NivelAcessoServiceImpl::class.java)
        situacaoService = mock(SituacaoService::class.java)
        horarioProfessorService = mock(HorarioProfessorService::class.java)
        service = UsuarioServiceImpl(usuarioRepository, nivelAcessoService, situacaoService, horarioProfessorService)
    }
    @Test
    fun `return user when authenticate`(){
        val usuario = UsuarioBuilder().build()
        val usuarioEsperado = UsuarioBuilder().withAutenticado(true).build()
        val usuarioResponseEsperado = UsuarioResponse.from(usuarioEsperado)

        `when`(usuarioRepository.autenticar(anyInt())).thenReturn(1)
        `when`(usuarioRepository.findById(anyInt())).thenReturn(Optional.of(usuarioEsperado))

        val resultado = service.autenticar(usuario.id!!)

        assertEquals(usuarioResponseEsperado, resultado)
    }

    @Test
    fun `return user when desauthenticate`(){
        val usuario = UsuarioBuilder().build()
        val usuarioEsperado = UsuarioBuilder().withAutenticado(false).build()
        val usuarioResponseEsperado = UsuarioResponse.from(usuarioEsperado)

        `when`(usuarioRepository.autenticar(anyInt())).thenReturn(1)
        `when`(usuarioRepository.findById(anyInt())).thenReturn(Optional.of(usuarioEsperado))

        val resultado = service.autenticar(usuario.id!!)

        assertEquals(usuarioResponseEsperado, resultado)
    }


    @Test
    fun `buscaUsuarios should return list of UsuarioResponse when users exist`() {
        val usuarios = listOf(UsuarioBuilder().build())
        `when`(usuarioRepository.findByNivelAcessoNome(NivelAcessoNome.PROFESSOR_AUXILIAR)).thenReturn(usuarios)

        val resultado = service.buscaUsuarios(NivelAcessoNome.PROFESSOR_AUXILIAR)

        assertEquals(1, resultado.size)
    }

    @Test
    fun `buscaUsuarios should throw ResponseStatusException when no users found`() {
        `when`(usuarioRepository.findByNivelAcessoNome(NivelAcessoNome.PROFESSOR_AUXILIAR)).thenReturn(emptyList())

        val exception = assertThrows(ResponseStatusException::class.java) {
            service.buscaUsuarios(NivelAcessoNome.PROFESSOR_AUXILIAR)
        }

        assertEquals(204, exception.statusCode.value())
    }
    @Test
    fun `salvaUsuario should save and return UsuarioResponse when no conflict`() {
        val novoUsuario = UsuarioBuilder().withCpf("123.456.789-00").build()
        `when`(usuarioRepository.findByCpf("123.456.789-00")).thenReturn(Optional.empty())
        `when`(usuarioRepository.save(novoUsuario)).thenReturn(novoUsuario)

        val resultado = service.salvaUsuario(novoUsuario)

        assertEquals(novoUsuario.cpf, resultado.cpf)
    }

}