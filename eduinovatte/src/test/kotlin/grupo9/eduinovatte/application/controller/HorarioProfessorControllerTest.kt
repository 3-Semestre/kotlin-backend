package grupo9.eduinovatte.controller

import com.example.demo.builder.HorarioProfessorBuilder
import grupo9.eduinovatte.model.HorarioProfessor
import org.hibernate.validator.internal.util.Contracts.assertTrue
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.http.HttpStatus

@ExtendWith(MockitoExtension::class)
class HorarioAlmocoControllerTest {

    @Mock
    lateinit var horarioProfessorService: HorarioProfessorService

    @InjectMocks
    lateinit var controller: HorarioAlmocoController

    @Test
    fun `test salvar`() {
        val novoHorarioProfessor = HorarioProfessorBuilder().build()

        val savedHorarioProfessor = HorarioProfessorBuilder().build()

        `when`(horarioProfessorService.salvar(novoHorarioProfessor)).thenReturn(savedHorarioProfessor)

        val response = controller.salvaUsuarioNicho(novoHorarioProfessor)

        assertEquals(HttpStatus.CREATED, response.statusCode)
        assertEquals(savedHorarioProfessor, response.body)
    }

    @Test
    fun `test buscarPorHorarioProfessor`() {
        val id = 1
        val horarioProfessor = HorarioProfessorBuilder().build()

        `when`(horarioProfessorService.buscaPorUsuario(id)).thenReturn(horarioProfessor)

        val response = controller.buscarPorHorarioProfessor(id)

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(horarioProfessor, response.body)
    }

    @Test
    fun `test editarHorarioProfessor`() {
        val novoHorarioProfessor = HorarioProfessorBuilder().build()

        val horarioProfessorEditado = HorarioProfessorBuilder().build()

        `when`(horarioProfessorService.edita(novoHorarioProfessor)).thenReturn(horarioProfessorEditado)

        val response = controller.editaHorarioProfessor(novoHorarioProfessor)

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(horarioProfessorEditado, response.body)
    }

    @Test
    fun `test buscaHorariosProfessor`() {
        val horarios = listOf(HorarioProfessorBuilder().build())

        `when`(horarioProfessorService.buscaHorarios()).thenReturn(horarios)

        val response = controller.buscaHorariosProfessor()

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(horarios, response.body)
    }

}
