package grupo9.eduinovatte.application.dto.response

import com.fasterxml.jackson.annotation.JsonIgnore
import grupo9.eduinovatte.domain.model.enums.StatusNome
import java.time.LocalDate
import java.time.LocalTime

data class AgendamentoAlunoResponse (
    var id: Int? = null,
    var data: String? = null,
    var dia_semana: String? = null,
    var horario_inicio: String? = null,
    var horario_fim: String? = null,
    var professor_nome: String? = null,
    var aluno_nome: String? = null
){
}