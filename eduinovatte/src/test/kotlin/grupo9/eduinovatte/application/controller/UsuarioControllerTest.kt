package grupo9.eduinovatte.application.controller

import grupo9.eduinovatte.application.dto.request.LoginForm
import grupo9.eduinovatte.application.dto.response.UsuarioResponse
import grupo9.eduinovatte.controller.UsuarioController
import grupo9.eduinovatte.domain.service.UsuarioService
import grupo9.eduinovatte.model.NivelAcesso
import grupo9.eduinovatte.model.UsuarioBuilder
import grupo9.eduinovatte.model.enums.NivelAcessoNome
import grupo9.eduinovatte.service.UsuarioRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.eq
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.web.server.ResponseStatusException
import java.util.*

class UsuarioControllerTest {
    lateinit var usuarioRepository: UsuarioRepository
    lateinit var usuarioService: UsuarioService
    lateinit var controller: UsuarioController

    @BeforeEach
    fun iniciar() {
        usuarioRepository = Mockito.mock(UsuarioRepository::class.java)
        usuarioService = Mockito.mock(UsuarioService::class.java)
        controller = UsuarioController(usuarioRepository, usuarioService)
    }

    @Test
    fun `autenticarUsuario method should return 201 and authenticated user`() {
        val loginForm = LoginForm(email = "email", senha = "senha", cpf = null)
        val usuario = UsuarioBuilder().withEmail(loginForm.email!!).withSenha(loginForm.senha).build()
        val usuarioResponse = UsuarioResponse.from(usuario)

        `when`(usuarioRepository.findByEmailOrCpfAndSenha("email", null, "senha")).thenReturn(usuario)
        `when`(usuarioService.autenticar(usuario.id!!)).thenReturn(usuarioResponse)
        `when`(usuarioService.validaSituacao(anyInt())).then{}
        `when`(usuarioService.validaNivelAcesso(anyInt(), eq(NivelAcessoNome.ALUNO))).then{}

        val response = controller.autenticarUsuario(loginForm)

        assert(response.statusCode.value() == 201)
    }

    @Test
    fun `autenticarUsuario method should return 403 when authentication fails`() {

        val loginForm = LoginForm(email = "email", senha = "senha", cpf = null)


        `when`(usuarioRepository.findByEmailOrCpfAndSenha("email", null, "senha")).thenThrow(
            EmptyResultDataAccessException(1)
        )

        val response = controller.autenticarUsuario(loginForm)

        Assertions.assertEquals(HttpStatus.FORBIDDEN, response.statusCode)

        Assertions.assertNull(response.body)
    }

    @Test
    fun `autenticarUsuario method should return 401 when authentication fails`() {

        val loginForm = LoginForm(email = "email", senha = "senha", cpf = null)
        val usuario = UsuarioBuilder().withEmail(loginForm.email!!).withSenha(loginForm.senha).build()

        `when`(usuarioRepository.findByEmailOrCpfAndSenha("email", null, "senha")).thenReturn(usuario)
        `when`(usuarioService.validaSituacao(anyInt())).thenThrow(ResponseStatusException(HttpStatusCode.valueOf(401)))


        val exception = Assertions.assertThrows(ResponseStatusException::class.java) {
            val response = controller.autenticarUsuario(loginForm)
        }

        Assertions.assertEquals(401, exception.statusCode.value())
    }
    @Test
    fun `Return all users when buscaUsuarios method`(){
        val tipo = "aluno"
        val lista = listOf(UsuarioBuilder().build())
        val listaResponse = usuarioService.retornaListaUsuario(lista)

        `when`(usuarioService.buscaUsuarios(NivelAcessoNome.ALUNO)).thenReturn(listaResponse)

        val resultado = controller.buscaUsuarios(tipo)

        assertEquals(200, resultado.statusCode.value())
    }

    @Test
    fun `Save user when salvaUsuario method with status 200`(){
        val tipo = "aluno"
        val usuario = UsuarioBuilder().build()
        val usuarioResponse = usuarioService.retornaUsuario(UsuarioBuilder().build())

        `when`(usuarioService.salvaUsuario(usuario)).thenReturn(usuarioResponse)
        `when`(usuarioService.validaNivelAcesso(anyInt(), eq(NivelAcessoNome.ALUNO))).then{}
        val resultado = controller.salvaUsuario(tipo, usuario)

        assertEquals(201, resultado.statusCode.value())
    }

    @Test
    fun `Edit user in editaUsuario method with status 200`(){
        val tipo = "aluno"
        val id = 1
        val usuarioAntigo = UsuarioBuilder().build()
        val usuarioNovo = UsuarioBuilder().withEmail("joao@email.com").build()
        val usuarioResponse = usuarioService.retornaUsuario(usuarioNovo)

        `when`(usuarioRepository.existsById(anyInt())).then{true}
        `when`(usuarioRepository.findById(anyInt())).then{Optional.of(usuarioAntigo)}
        `when`(usuarioService.validaNivelAcesso(anyInt(), eq(NivelAcessoNome.ALUNO))).then{}
        `when`(usuarioService.editaUsuario(usuarioNovo)).thenReturn(usuarioResponse)
        val resultado = controller.editaUsuario(tipo, id, usuarioNovo)

        assertEquals(200, resultado.statusCode.value())
    }

    @Test
    fun `Return 404 when user don't exists in editaUsuario method`(){
        val tipo = "aluno"
        val id = 1
        val usuarioNovo = UsuarioBuilder().withEmail("joao@email.com").build()

        `when`(usuarioRepository.existsById(anyInt())).then{null}
        val resultado = controller.editaUsuario(tipo, id, usuarioNovo)

        assertEquals(404, resultado.statusCode.value())
    }

    @Test
    fun `Return 401 when user from paramether have diferent acess than the body user in editaUsuario method`(){
        val tipo = "aluno"
        val id = 1
        val usuarioAntigo = UsuarioBuilder().build()
        val usuarioNovo = UsuarioBuilder().withEmail("joao@email.com").withNivelAcesso(NivelAcesso(id=2, nome = NivelAcessoNome.PROFESSOR_AUXILIAR)).build()

        `when`(usuarioRepository.existsById(anyInt())).then{true}
        `when`(usuarioRepository.findById(anyInt())).then{Optional.of(usuarioAntigo)}

        val resultado = controller.editaUsuario(tipo, id, usuarioNovo)

        assertEquals(401, resultado.statusCode.value())
    }


}