package grupo9.eduinovatte.application.dto.request

import grupo9.eduinovatte.domain.model.Status
import grupo9.eduinovatte.model.Agendamento

data class AlterarStatus (
    val novoAgendamento: Agendamento,
    val status: Status
)