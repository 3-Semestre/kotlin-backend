package grupo9.eduinovatte.domain.service

import grupo9.eduinovatte.application.dto.response.UsuarioResponse
import grupo9.eduinovatte.controller.HorarioProfessorService
import grupo9.eduinovatte.model.HorarioProfessor
import grupo9.eduinovatte.model.NivelAcesso
import grupo9.eduinovatte.model.Situacao
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

class UsuarioServiceTest {

    lateinit var usuarioRepository: UsuarioRepository
    lateinit var nivelAcessoService: NivelAcessoService
    lateinit var situacaoService: SituacaoService
    lateinit var horarioProfessorService: HorarioProfessorService
    lateinit var service: UsuarioService

    @BeforeEach
    fun iniciar() {
        usuarioRepository = mock(UsuarioRepository::class.java)
        nivelAcessoService = mock(NivelAcessoService::class.java)
        situacaoService = mock(SituacaoService::class.java)
        horarioProfessorService = mock(HorarioProfessorService::class.java)
        service = UsuarioService(usuarioRepository, nivelAcessoService, situacaoService, horarioProfessorService)
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
    fun `validaSituacao should throw ResponseStatusException when situacao is INATIVO`() {
        val situacao = Situacao(id = 1, nome = SituacaoNome.INATIVO)
        `when`(situacaoService.buscaPorId(1)).thenReturn(situacao)

        val exception = assertThrows(ResponseStatusException::class.java) {
            service.validaSituacao(1)
        }

        assertEquals(401, exception.statusCode.value())
    }

    @Test
    fun `validaSituacao should not throw when situacao is not INATIVO`() {
        val situacao = Situacao(id = 1, nome = SituacaoNome.ATIVO)
        `when`(situacaoService.buscaPorId(1)).thenReturn(situacao)

        service.validaSituacao(1)
    }

    @Test
    fun `validaNivelAcesso should throw ResponseStatusException when nivelAcesso is not expected`() {
        val nivelAcesso = NivelAcesso(id = 1, nome = NivelAcessoNome.ALUNO)
        `when`(nivelAcessoService.buscaPorId(anyInt())).thenReturn(nivelAcesso)

        val exception = assertThrows(ResponseStatusException::class.java) {
            service.validaNivelAcesso(1, NivelAcessoNome.PROFESSOR_AUXILIAR)
        }

        assertEquals(401, exception.statusCode.value())
    }

    @Test
    fun `validaNivelAcesso should not throw when nivelAcesso is expected`() {
        val nivelAcesso = NivelAcesso(id = 1, nome = NivelAcessoNome.PROFESSOR_AUXILIAR)
        `when`(nivelAcessoService.buscaPorId(anyInt())).thenReturn(nivelAcesso)

        service.validaNivelAcesso(1, NivelAcessoNome.PROFESSOR_AUXILIAR)
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
        `when`(usuarioRepository.findByCpf("123.456.789-00")).thenReturn(null)
        `when`(usuarioRepository.save(novoUsuario)).thenReturn(novoUsuario)

        val resultado = service.salvaUsuario(novoUsuario)

        assertEquals(novoUsuario.cpf, resultado.cpf)
    }

    @Test
    fun `salvaUsuario should throw ResponseStatusException when cpf conflict`() {
        val novoUsuario = UsuarioBuilder().withCpf("123.456.789-00").build()
        `when`(usuarioRepository.findByCpf("123.456.789-00")).thenReturn(novoUsuario)

        val exception = assertThrows(ResponseStatusException::class.java) {
            service.salvaUsuario(novoUsuario)
        }

        assertEquals(409, exception.statusCode.value())
    }
}