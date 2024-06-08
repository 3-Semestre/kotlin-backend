package grupo9.eduinovatte.application.controller

import com.example.demo.builder.NivelAcessoBuilder
import grupo9.eduinovatte.controller.NivelAcessoController
import grupo9.eduinovatte.domain.service.NivelAcessoService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`

class NivelAcessoControllerTest {
    lateinit var nivelAcessoService: NivelAcessoService
    lateinit var controller: NivelAcessoController

    @BeforeEach
    fun iniciar() {
        nivelAcessoService = Mockito.mock(NivelAcessoService::class.java)
        controller = NivelAcessoController(nivelAcessoService)
    }

    @Test
    fun `should return 204 when don't have situacao in buscaSituacao method`(){

        `when`(nivelAcessoService.buscaNiveisAcesso()).thenReturn(emptyList())

        val resultado = controller.buscaNiveisAcesso()

        assertEquals(204, resultado.statusCode.value())
    }

    @Test
    fun `should return 200 when have situacao in buscaSituacao method`(){
        val niveis = NivelAcessoBuilder.todasOsNiveisAcesso()

        `when`(nivelAcessoService.buscaNiveisAcesso()).thenReturn(niveis)

        val resultado = controller.buscaNiveisAcesso()

        assertEquals(200, resultado.statusCode.value())
    }
}