package grupo9.eduinovatte.controller

import com.example.demo.builder.NichoBuilder
import grupo9.eduinovatte.model.UsuarioBuilder
import grupo9.eduinovatte.domain.model.entity.UsuarioNicho
import grupo9.eduinovatte.domain.service.impl.UsuarioNichoServiceImpl
import grupo9.eduinovatte.service.UsuarioNichoRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.Mockito.*

class UsuarioNichoServiceImplTest {

    @Mock
    private lateinit var usuarioNichoRepository: UsuarioNichoRepository

    @InjectMocks
    private lateinit var usuarioNichoService: UsuarioNichoServiceImpl

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }
}
