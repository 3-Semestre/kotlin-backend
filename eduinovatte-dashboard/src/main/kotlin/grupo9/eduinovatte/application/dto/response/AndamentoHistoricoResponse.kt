package grupo9.eduinovatte.application.dto.response

import com.fasterxml.jackson.annotation.JsonIgnore
import grupo9.eduinovatte.domain.model.Status
import java.time.LocalDateTime

data class AndamentoHistoricoResponse (
    var id: Int? = null,
    var dataAtualizacao: LocalDateTime? = null,
    var status: Status? = null,
    var agendamento: AgendamentoSemStatus? = null

)