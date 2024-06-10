package grupo9.eduinovatte.application.dto.request

import grupo9.eduinovatte.model.Agendamento
import grupo9.eduinovatte.model.Usuario
import java.time.LocalDate
import java.time.LocalTime

data class AgendamentoCadastro (
    var id: Int? = null,

    var data: LocalDate?,

    var horarioInicio: LocalTime?,

    var horarioFim: LocalTime?,

    var professor: Usuario?,

    var aluno: Usuario?
){
    companion object {
        fun from(agendamento: Agendamento): AgendamentoCadastro {
            return AgendamentoCadastro(
                id = agendamento.id,
                data = agendamento.data,
                horarioInicio = agendamento.horarioInicio,
                horarioFim = agendamento.horarioFim,
                professor = agendamento.professor,
                aluno = agendamento.aluno
            )
        }
    }
}