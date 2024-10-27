package grupo9.eduinovatte.application.dto.request

import org.jetbrains.annotations.NotNull

data class AgendamentoTransferenciaRequest (
    @NotNull
    val idAgendamento: Int,
    @NotNull
    val novoProfessorId: Int
)