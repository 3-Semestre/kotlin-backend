package grupo9.eduinovatte.application.dto.request

import grupo9.eduinovatte.domain.model.entity.Status
import grupo9.eduinovatte.domain.model.entity.Agendamento

data class AlterarStatus (
    val novoAgendamento: Agendamento,
    val status: Status
)