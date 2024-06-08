package grupo9.eduinovatte.domain.service

import com.example.demo.builder.SituacaoBuilder
import grupo9.eduinovatte.service.SituacaoRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import java.util.*

class SituacaoServiceTest {
    lateinit var situacaoRepository: SituacaoRepository
    lateinit var service: SituacaoService

    @BeforeEach
    fun iniciar() {
        situacaoRepository = Mockito.mock(SituacaoRepository::class.java)
        service = SituacaoService(situacaoRepository)
    }

    @Test
    fun `search all situacoes`(){
        val situacoes = SituacaoBuilder.todasAsSituacoes()

        `when`(situacaoRepository.findAll()).thenReturn(situacoes)

        val resultado = service.buscaSituacoes()

        assertEquals(situacoes.size, resultado.size)
    }

    @Test
    fun `search situacao by id`(){
        val situacao = SituacaoBuilder().build()

        `when`(situacaoRepository.findById(anyInt())).thenReturn(Optional.of(situacao))

        val resultado = service.buscaPorId(1)
        assertEquals(situacao, resultado)
    }
}