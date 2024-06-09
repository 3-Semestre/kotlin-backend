package grupo9.eduinovatte.application.dto.response

import com.fasterxml.jackson.annotation.JsonIgnore
import grupo9.eduinovatte.domain.model.enums.StatusNome
import java.time.LocalDate
import java.time.LocalTime

data class AgendamentoSemStatus (
    var id: Int? = null,
    var data: LocalDate? = null,
    var horarioInicio: LocalTime? = null,
    var horarioFim: LocalTime? = null,
    @JsonIgnore var historico: List<AndamentoResponse>? = null,
    var professor: UsuarioNomeSemDetalhesResponse? = null,
    var aluno: UsuarioNomeSemDetalhesResponse? = null
)