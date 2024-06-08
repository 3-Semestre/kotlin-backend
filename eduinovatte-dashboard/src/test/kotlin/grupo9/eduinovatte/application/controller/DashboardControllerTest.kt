package grupo9.eduinovatte.controller

import grupo9.eduinovatte.application.dto.response.UsuarioResponse
import grupo9.eduinovatte.domain.service.UsuarioService
import grupo9.eduinovatte.model.UsuarioBuilder
import grupo9.eduinovatte.model.enums.NivelAcessoNome
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.http.HttpStatus
import java.time.LocalDate
@ExtendWith(MockitoExtension::class)
class DashboardControllerTest {

    @Mock
    lateinit var usuarioService: UsuarioService

    @Mock
    lateinit var usuarioController: UsuarioController

    @InjectMocks
    lateinit var dashboardController: DashboardController

    @Test
    fun `test buscaUsuariosNovoPelaDataCorte - Success`() {
        val tipo = "ADMIN"
        val dataCorte = LocalDate.now().minusDays(1)
        val tipoAcesso = NivelAcessoNome.PROFESSOR_AUXILIAR
        val usuarioResponses = listOf(UsuarioResponse.from(UsuarioBuilder().build()))

        `when`(usuarioController.retornaNivelAcessoNome(tipo)).thenReturn(tipoAcesso)
        `when`(usuarioService.buscaUsuariosNovos(tipoAcesso, dataCorte)).thenReturn(usuarioResponses)

        val response = dashboardController.buscaUsuariosNovoPelaDataCorte(tipo, dataCorte)

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(usuarioResponses, response.body)
    }

    @Test
    fun `test buscaUsuariosNovoPelaDataCorte - Forbidden`() {
        val tipo = "INVALID"
        val dataCorte = LocalDate.now().minusMonths(1)

        `when`(usuarioController.retornaNivelAcessoNome(tipo)).thenReturn(null)

        val response = dashboardController.buscaUsuariosNovoPelaDataCorte(tipo, dataCorte)

        assertEquals(HttpStatus.FORBIDDEN, response.statusCode)
        assertTrue(response.body == null)
    }

    @Test
    fun `test buscaUsuariosNovoNoUltimoMes - Success`() {
        val tipo = "ADMIN"
        val tipoAcesso = NivelAcessoNome.PROFESSOR_AUXILIAR
        val dataCorte = LocalDate.now().minusMonths(1)
        val usuarioResponses = listOf(UsuarioResponse.from(UsuarioBuilder().build()))


        `when`(usuarioController.retornaNivelAcessoNome(tipo)).thenReturn(tipoAcesso)
        `when`(usuarioService.buscaUsuariosNovos(tipoAcesso, dataCorte)).thenReturn(usuarioResponses)

        val response = dashboardController.buscaUsuariosNovoNoUltimoMes(tipo)

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(usuarioResponses, response.body)
    }

    @Test
    fun `test buscaUsuariosNovoNoUltimoMes - Forbidden`() {
        val tipo = "INVALID"

        `when`(usuarioController.retornaNivelAcessoNome(tipo)).thenReturn(null)

        val response = dashboardController.buscaUsuariosNovoNoUltimoMes(tipo)

        assertEquals(HttpStatus.FORBIDDEN, response.statusCode)
        assertTrue(response.body == null)
    }

    @Test
    fun `test buscaUsuariosNovoNoUltimoAno - Success`() {
        val tipo = "ADMIN"
        val tipoAcesso = NivelAcessoNome.PROFESSOR_AUXILIAR
        val dataCorte = LocalDate.now().minusYears(1)
        val usuarioResponses = listOf(UsuarioResponse.from(UsuarioBuilder().build()))


        `when`(usuarioController.retornaNivelAcessoNome(tipo)).thenReturn(tipoAcesso)
        `when`(usuarioService.buscaUsuariosNovos(tipoAcesso, dataCorte)).thenReturn(usuarioResponses)

        val response = dashboardController.buscaUsuariosNovoNoUltimoAno(tipo)

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(usuarioResponses, response.body)
    }

    @Test
    fun `test buscaUsuariosNovoNoUltimoAno - Forbidden`() {
        val tipo = "INVALID"

        `when`(usuarioController.retornaNivelAcessoNome(tipo)).thenReturn(null)

        val response = dashboardController.buscaUsuariosNovoNoUltimoAno(tipo)

        assertEquals(HttpStatus.FORBIDDEN, response.statusCode)
        assertTrue(response.body == null)
    }
}
