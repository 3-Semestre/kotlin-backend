package grupo9.eduinovatte.controller

import com.example.demo.builder.HorarioProfessorBuilder
import grupo9.eduinovatte.domain.repository.HorarioProfessorRepository
import grupo9.eduinovatte.domain.service.SituacaoService
import grupo9.eduinovatte.model.HorarioProfessor
import grupo9.eduinovatte.model.Usuario
import grupo9.eduinovatte.service.SituacaoRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

@ExtendWith(MockitoExtension::class)
class HorarioProfessorServiceTest {

    lateinit var horarioProfessorRepository: HorarioProfessorRepository
    lateinit var service: HorarioProfessorService

    @BeforeEach
    fun iniciar() {
        horarioProfessorRepository = Mockito.mock(HorarioProfessorRepository::class.java)
        service = HorarioProfessorService(horarioProfessorRepository)
    }

    @Test
    fun `test buscaPorUsuario`() {
        val horarioProfessor = HorarioProfessorBuilder().build()
        `when`(horarioProfessorRepository.findByUsuarioId(1)).thenReturn(horarioProfessor)

        val foundHorarioProfessor = service.buscaPorUsuario(1)

        assertEquals(horarioProfessor, foundHorarioProfessor)
    }

    @Test
    fun `test edita`() {
        val horarioProfessor = HorarioProfessorBuilder().build()
        `when`(horarioProfessorRepository.existsById(1)).thenReturn(true)
        `when`(horarioProfessorRepository.save(horarioProfessor)).thenReturn(horarioProfessor)

        val editedHorarioProfessor = service.edita(horarioProfessor)

        assertEquals(horarioProfessor, editedHorarioProfessor)
    }

    @Test
    fun `test edita falha de não encontrado`() {
        val horarioProfessor = HorarioProfessorBuilder().build()
        `when`(horarioProfessorRepository.existsById(1)).thenReturn(false)

        try {
            service.edita(horarioProfessor)
        } catch (ex: ResponseStatusException) {
            assertEquals(HttpStatus.NO_CONTENT, ex.statusCode)
        }
    }

    @Test
    fun `test buscaHorarios`() {
        val horarioProfessor = HorarioProfessorBuilder().build()
        val horarios = listOf(horarioProfessor)

        `when`(horarioProfessorRepository.findAll()).thenReturn(horarios)

        val foundHorarios = service.buscaHorarios()

        assertEquals(horarios, foundHorarios)
    }

    @Test
    fun `test buscaHorarios falha de não encontrado`() {
        `when`(horarioProfessorRepository.findAll()).thenReturn(emptyList())

        try {
            service.buscaHorarios()
        } catch (ex: ResponseStatusException) {
            assertEquals(HttpStatus.NO_CONTENT, ex.statusCode)
        }
    }

}
