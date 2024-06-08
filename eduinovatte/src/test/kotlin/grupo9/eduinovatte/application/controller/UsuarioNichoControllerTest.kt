package grupo9.eduinovatte.application.controller

import com.example.demo.builder.NichoBuilder
import grupo9.eduinovatte.controller.UsuarioController
import grupo9.eduinovatte.controller.UsuarioNichoController
import grupo9.eduinovatte.controller.UsuarioNichoService
import grupo9.eduinovatte.domain.service.UsuarioNivelInglesService
import grupo9.eduinovatte.domain.service.UsuarioService
import grupo9.eduinovatte.model.UsuarioBuilder
import grupo9.eduinovatte.model.UsuarioNicho
import grupo9.eduinovatte.service.UsuarioRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mockito
import org.mockito.Mockito.doNothing
import org.mockito.Mockito.`when`
import org.springframework.http.HttpStatus

class UsuarioNichoControllerTest {

    lateinit var usuarioNichoService: UsuarioNichoService
    lateinit var controller: UsuarioNichoController

    @BeforeEach
    fun iniciar() {
        usuarioNichoService = Mockito.mock(UsuarioNichoService::class.java)
        controller = UsuarioNichoController(usuarioNichoService)
    }

    @Test
    fun `salvaUsuarioNicho deve retornar status 201 quando o usuario for salvo com sucesso`() {
        val usuarioNicho = UsuarioNicho(1,UsuarioBuilder().build(), NichoBuilder().build())
        `when`(usuarioNichoService.salvar(usuarioNicho)).thenReturn(usuarioNicho)

        val response = controller.salvaUsuarioNicho(usuarioNicho)

        assertEquals(HttpStatus.CREATED, response.statusCode)
        assertEquals(usuarioNicho, response.body)
    }

    @Test
    fun `buscarUsuarioNicho deve retornar status 200 com lista de usuarios`() {
        val usuarioNicho = UsuarioNicho(1,UsuarioBuilder().build(), NichoBuilder().build())
        val usuariosNichos = listOf(usuarioNicho)
        `when`(usuarioNichoService.buscaUsuariosNichos()).thenReturn(usuariosNichos)

        val response = controller.buscarUsuarioNicho()

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(usuariosNichos, response.body)
    }

    @Test
    fun `buscarUsuarioNicho deve retornar status 204 quando nao houver usuarios`() {
        `when`(usuarioNichoService.buscaUsuariosNichos()).thenReturn(emptyList())

        val response = controller.buscarUsuarioNicho()

        assertEquals(HttpStatus.NO_CONTENT, response.statusCode)
        assertNull(response.body)
    }

    @Test
    fun `buscarUsuarioPeloNicho deve retornar status 200 com lista de usuarios`() {
        val usuarioNicho = UsuarioNicho(1,UsuarioBuilder().build(), NichoBuilder().build())
        val usuariosNichos = listOf(usuarioNicho)
        `when`(usuarioNichoService.buscaPorNicho(1)).thenReturn(usuariosNichos)

        val response = controller.buscarUsuarioPeloNicho(1)

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(usuariosNichos, response.body)
    }

    @Test
    fun `buscarUsuarioPeloNicho deve retornar status 204 quando nao houver usuarios`() {
        `when`(usuarioNichoService.buscaPorNicho(anyInt())).thenReturn(emptyList())

        val response = controller.buscarUsuarioPeloNicho(1)

        assertEquals(HttpStatus.NO_CONTENT, response.statusCode)
        assertNull(response.body)
    }

    @Test
    fun `buscarUsuarioPeloId deve retornar status 200 com lista de usuarios`() {
        val usuarioNicho = UsuarioNicho(1,UsuarioBuilder().build(), NichoBuilder().build())
        val usuariosNichos = listOf(usuarioNicho)
        `when`(usuarioNichoService.buscaPorIdUsuario(anyInt())).thenReturn(usuariosNichos)

        val response = controller.buscarUsuarioPeloId(1)

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(usuariosNichos, response.body)
    }

    @Test
    fun `buscarUsuarioPeloId deve retornar status 204 quando nao houver usuarios`() {
        `when`(usuarioNichoService.buscaPorIdUsuario(anyInt())).thenReturn(emptyList())

        val response = controller.buscarUsuarioPeloId(1)

        assertEquals(HttpStatus.NO_CONTENT, response.statusCode)
        assertNull(response.body)
    }

    @Test
    fun `deleta deve retornar status 204`() {
        doNothing().`when`(usuarioNichoService).deleta(anyInt())

        val response = controller.deleta(1)

        assertEquals(HttpStatus.NO_CONTENT, response.statusCode)
    }
}