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

}
