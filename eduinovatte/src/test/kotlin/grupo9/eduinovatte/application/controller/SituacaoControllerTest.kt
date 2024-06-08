package grupo9.eduinovatte.application.controller

import com.example.demo.builder.SituacaoBuilder
import grupo9.eduinovatte.controller.SituacaoController
import grupo9.eduinovatte.domain.service.SituacaoService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`

class SituacaoControllerTest {
    lateinit var situacaoService: SituacaoService
    lateinit var controller: SituacaoController

    @BeforeEach
    fun iniciar() {
        situacaoService = Mockito.mock(SituacaoService::class.java)
        controller = SituacaoController(situacaoService)
    }

    @Test
    fun `should return 204 when don't have situacao in buscaSituacao method`(){

        `when`(situacaoService.buscaSituacoes()).thenReturn(emptyList())

        val resultado = controller.buscaSituacao()

        assertEquals(204, resultado.statusCode.value())
    }

    @Test
    fun `should return 200 when have situacao in buscaSituacao method`(){
        val situacoes = SituacaoBuilder.todasAsSituacoes()

        `when`(situacaoService.buscaSituacoes()).thenReturn(situacoes)

        val resultado = controller.buscaSituacao()

        assertEquals(200, resultado.statusCode.value())
    }
}