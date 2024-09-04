package grupo9.eduinovatte.application.dto.response

import com.fasterxml.jackson.annotation.JsonIgnore
import grupo9.eduinovatte.domain.model.entity.Status
import java.time.LocalDateTime

data class AndamentoResponse (
    var id: Int? = null,
    var dataAtualizacao: LocalDateTime? = null,
    @JsonIgnore var status: Status? = null,
    var agendamento: AgendamentoListagemResponse? = null
)