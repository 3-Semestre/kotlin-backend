package grupo9.eduinovatte.application.dto.response

import grupo9.eduinovatte.domain.model.Status
import java.time.LocalDate

data class HistoricoAgendamentoResponse (
    var id: Int? = null,
    var dataAtualizacao: LocalDate? = null,
    var status: Status? = null,
    var agendamento: AgendamentoListagemResponse? = null
)