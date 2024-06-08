package grupo9.eduinovatte.application.controller

import com.example.demo.builder.NivelInglesBuilder
import grupo9.eduinovatte.domain.model.UsuarioNivelIngles
import grupo9.eduinovatte.domain.service.UsuarioNivelInglesService
import grupo9.eduinovatte.model.UsuarioBuilder
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mockito
import org.mockito.Mockito.doNothing
import org.mockito.Mockito.`when`
import org.springframework.http.HttpStatus

class UsuarioNivelInglesControllerTest {

    lateinit var usuarioNivelInglesService: UsuarioNivelInglesService
    lateinit var controller: UsuarioNivelInglesController

    @BeforeEach
    fun iniciar() {
        usuarioNivelInglesService = Mockito.mock(UsuarioNivelInglesService::class.java)
        controller = UsuarioNivelInglesController(usuarioNivelInglesService)
    }

    @Test
    fun `salvaUsuarioNivelIngles deve retornar status 201 quando o usuario for salvo com sucesso`() {
        val usuarioNivelIngles = UsuarioNivelIngles(1,UsuarioBuilder().build(), NivelInglesBuilder().build())
        `when`(usuarioNivelInglesService.salvar(usuarioNivelIngles)).thenReturn(usuarioNivelIngles)

        val response = controller.salvaUsuarioNivelIngles(usuarioNivelIngles)

        assertEquals(HttpStatus.CREATED, response.statusCode)
        assertEquals(usuarioNivelIngles, response.body)
    }

    @Test
    fun `buscarUsuarioNivelIngles deve retornar status 200 com lista de usuarios`() {
        val usuarioNivelIngles = UsuarioNivelIngles(1,UsuarioBuilder().build(), NivelInglesBuilder().build())
        val usuariosNivelIngles = listOf(usuarioNivelIngles)
        `when`(usuarioNivelInglesService.buscaNiveisIngles()).thenReturn(usuariosNivelIngles)

        val response = controller.buscarNivelIngles()

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(usuariosNivelIngles, response.body)
    }

    @Test
    fun `buscarUsuarioNivelIngles deve retornar status 204 quando nao houver usuarios`() {
        `when`(usuarioNivelInglesService.buscaNiveisIngles()).thenReturn(emptyList())

        val response = controller.buscarNivelIngles()

        assertEquals(HttpStatus.NO_CONTENT, response.statusCode)
        assertNull(response.body)
    }

    @Test
    fun `buscarUsuarioPeloNivelIngles deve retornar status 200 com lista de usuarios`() {
        val usuarioNivelIngles = UsuarioNivelIngles(1,UsuarioBuilder().build(), NivelInglesBuilder().build())
        val usuariosNivelIngles = listOf(usuarioNivelIngles)
        `when`(usuarioNivelInglesService.buscaPorNivelIngles(1)).thenReturn(usuariosNivelIngles)

        val response = controller.buscarUsuarioPeloNivelIngles(1)

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(usuariosNivelIngles, response.body)
    }

    @Test
    fun `buscarUsuarioPeloNivelIngles deve retornar status 204 quando nao houver usuarios`() {
        `when`(usuarioNivelInglesService.buscaPorNivelIngles(anyInt())).thenReturn(emptyList())

        val response = controller.buscarUsuarioPeloNivelIngles(1)

        assertEquals(HttpStatus.NO_CONTENT, response.statusCode)
        assertNull(response.body)
    }

    @Test
    fun `buscarUsuarioPeloId deve retornar status 200 com lista de usuarios`() {
        val usuarioNivelIngles = UsuarioNivelIngles(1,UsuarioBuilder().build(), NivelInglesBuilder().build())
        val usuariosNivelIngles = listOf(usuarioNivelIngles)
        `when`(usuarioNivelInglesService.buscaPorIdUsuario(anyInt())).thenReturn(usuariosNivelIngles)

        val response = controller.buscarUsuarioPeloId(1)

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(usuariosNivelIngles, response.body)
    }

    @Test
    fun `buscarUsuarioPeloId deve retornar status 204 quando nao houver usuarios`() {
        `when`(usuarioNivelInglesService.buscaPorIdUsuario(anyInt())).thenReturn(emptyList())

        val response = controller.buscarUsuarioPeloId(1)

        assertEquals(HttpStatus.NO_CONTENT, response.statusCode)
        assertNull(response.body)
    }

    @Test
    fun `deleta deve retornar status 204`() {
        doNothing().`when`(usuarioNivelInglesService).deleta(anyInt())

        val response = controller.deleta(1)

        assertEquals(HttpStatus.NO_CONTENT, response.statusCode)
    }
}