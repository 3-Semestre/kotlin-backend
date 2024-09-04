package grupo9.eduinovatte.controller

import com.example.demo.builder.NichoBuilder
import grupo9.eduinovatte.model.UsuarioBuilder
import grupo9.eduinovatte.domain.model.entity.UsuarioNicho
import grupo9.eduinovatte.service.UsuarioNichoRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.Mockito.*

class UsuarioNichoServiceTest {

    @Mock
    private lateinit var usuarioNichoRepository: UsuarioNichoRepository

    @InjectMocks
    private lateinit var usuarioNichoService: UsuarioNichoService

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `salvar deve retornar usuario nicho salvo`() {
        val usuarioNicho = UsuarioNicho(1, UsuarioBuilder().build(), NichoBuilder().build())
        `when`(usuarioNichoRepository.save(usuarioNicho)).thenReturn(usuarioNicho)

        val result = usuarioNichoService.salvar(usuarioNicho)

        assertNotNull(result)
        verify(usuarioNichoRepository, times(1)).save(usuarioNicho)
    }

    @Test
    fun `buscaPorNicho deve retornar lista de usuario nicho`() {
        val usuarioNichos = listOf(
            UsuarioNicho(1, UsuarioBuilder().build(), NichoBuilder().build()),
            UsuarioNicho(2, UsuarioBuilder().build(), NichoBuilder().build())
        )
        `when`(usuarioNichoRepository.findByNichoId(1)).thenReturn(usuarioNichos)

        val result = usuarioNichoService.buscaPorNicho(1)

        assertNotNull(result)
        assertEquals(2, result.size)
        verify(usuarioNichoRepository, times(1)).findByNichoId(1)
    }

    @Test
    fun `buscaUsuariosNichos deve retornar lista de todos os usuario nicho`() {
        val usuarioNichos = listOf(
            UsuarioNicho(1, UsuarioBuilder().build(), NichoBuilder().build()),
            UsuarioNicho(2, UsuarioBuilder().build(), NichoBuilder().build())
        )
        `when`(usuarioNichoRepository.findAll()).thenReturn(usuarioNichos)

        val result = usuarioNichoService.buscaUsuariosNichos()

        assertNotNull(result)
        assertEquals(2, result.size)
        verify(usuarioNichoRepository, times(1)).findAll()
    }

    @Test
    fun `buscaPorIdUsuario deve retornar lista de usuario nicho pelo id do usuario`() {
        val usuarioNichos = listOf(
            UsuarioNicho(1, UsuarioBuilder().build(), NichoBuilder().build()),
            UsuarioNicho(2, UsuarioBuilder().build(), NichoBuilder().build())
        )
        `when`(usuarioNichoRepository.findByUsuarioId(1)).thenReturn(usuarioNichos)

        val result = usuarioNichoService.buscaPorIdUsuario(1)

        assertNotNull(result)
        assertEquals(2, result.size)
        verify(usuarioNichoRepository, times(1)).findByUsuarioId(1)
    }

    @Test
    fun `deleta deve chamar repositorio para deletar usuario nicho`() {
        doNothing().`when`(usuarioNichoRepository).deleteById(1)

        usuarioNichoService.deleta(1)

        verify(usuarioNichoRepository, times(1)).deleteById(1)
    }
}
