package grupo9.eduinovatte.domain.service

import com.example.demo.builder.NivelInglesBuilder
import grupo9.eduinovatte.domain.model.entity.UsuarioNivelIngles
import grupo9.eduinovatte.domain.repository.UsuarioNivelInglesRepository
import grupo9.eduinovatte.domain.service.impl.UsuarioNivelInglesServiceImpl
import grupo9.eduinovatte.model.UsuarioBuilder
import org.hibernate.validator.internal.util.Contracts.assertNotNull
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.Mockito.*

class UsuarioNivelInglesServiceImplTest {

    @Mock
    private lateinit var usuarioNivelInglesRepository: UsuarioNivelInglesRepository

    @InjectMocks
    private lateinit var usuarioNivelInglesService: UsuarioNivelInglesServiceImpl

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `salvar deve retornar usuario nivel ingles salvo`() {
        val usuarioNivelIngles = UsuarioNivelIngles(1, UsuarioBuilder().build(), NivelInglesBuilder().build())
        `when`(usuarioNivelInglesRepository.save(usuarioNivelIngles)).thenReturn(usuarioNivelIngles)

        val result = usuarioNivelInglesService.salvar(usuarioNivelIngles)

        assertNotNull(result)
        verify(usuarioNivelInglesRepository, times(1)).save(usuarioNivelIngles)
    }

    @Test
    fun `buscaPorNivelIngles deve retornar lista de usuario nivel ingles`() {
        val usuarioNiveisIngles = listOf(
            UsuarioNivelIngles(1, UsuarioBuilder().build(), NivelInglesBuilder().build()),
            UsuarioNivelIngles(2, UsuarioBuilder().build(), NivelInglesBuilder().build())
        )
        `when`(usuarioNivelInglesRepository.findByNivelInglesId(1)).thenReturn(usuarioNiveisIngles)

        val result = usuarioNivelInglesService.buscaPorNivelIngles(1)

        assertNotNull(result)
        assertEquals(2, result.size)
        verify(usuarioNivelInglesRepository, times(1)).findByNivelInglesId(1)
    }

    @Test
    fun `buscaNiveisIngles deve retornar lista de todos os usuario nivel ingles`() {
        val usuarioNiveisIngles = listOf(
            UsuarioNivelIngles(1, UsuarioBuilder().build(), NivelInglesBuilder().build()),
            UsuarioNivelIngles(2, UsuarioBuilder().build(), NivelInglesBuilder().build())
        )
        `when`(usuarioNivelInglesRepository.findAll()).thenReturn(usuarioNiveisIngles)

        val result = usuarioNivelInglesService.buscaNiveisIngles()

        assertNotNull(result)
        assertEquals(2, result.size)
        verify(usuarioNivelInglesRepository, times(1)).findAll()
    }

    @Test
    fun `buscaPorIdUsuario deve retornar lista de usuario nivel ingles pelo id do usuario`() {
        val usuarioNiveisIngles = listOf(
            UsuarioNivelIngles(1, UsuarioBuilder().build(), NivelInglesBuilder().build()),
            UsuarioNivelIngles(2, UsuarioBuilder().build(), NivelInglesBuilder().build())
        )
        `when`(usuarioNivelInglesRepository.findByUsuarioId(1)).thenReturn(usuarioNiveisIngles)

        val result = usuarioNivelInglesService.buscaPorIdUsuario(1)

        assertNotNull(result)
        assertEquals(2, result.size)
        verify(usuarioNivelInglesRepository, times(1)).findByUsuarioId(1)
    }

    @Test
    fun `deleta deve chamar repositorio para deletar usuario nivel ingles`() {
        doNothing().`when`(usuarioNivelInglesRepository).deleteById(1)

        usuarioNivelInglesService.deleta(1)

        verify(usuarioNivelInglesRepository, times(1)).deleteById(1)
    }
}
