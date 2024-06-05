package grupo9.eduinovatte.domain.service

import com.example.demo.builder.NivelAcessoBuilder
import grupo9.eduinovatte.service.NivelAcessoRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import java.util.*

class NivelAcessoServiceTest {
    lateinit var nivelAcessoRepository: NivelAcessoRepository
    lateinit var service: NivelAcessoService

    @BeforeEach
    fun iniciar() {
        nivelAcessoRepository = Mockito.mock(NivelAcessoRepository::class.java)
        service = NivelAcessoService(nivelAcessoRepository)
    }

    @Test
    fun `search all niveis de acesso`(){
        val niveisAcesso = NivelAcessoBuilder.todasOsNiveisAcesso()

        `when`(nivelAcessoRepository.findAll()).thenReturn(niveisAcesso)

        val resultado = service.buscaNiveisAcesso()

        assertEquals(niveisAcesso.size, resultado.size)
    }

    @Test
    fun `search situacao by id`(){
        val nivel = NivelAcessoBuilder().build()

        `when`(nivelAcessoRepository.findById(anyInt())).thenReturn(Optional.of(nivel))

        val resultado = service.buscaPorId(1)
        assertEquals(nivel, resultado)
    }
}