package grupo9.eduinovatte.application.dto.request

import grupo9.eduinovatte.model.enums.NichoNome
import grupo9.eduinovatte.model.enums.NivelInglesNome
import java.time.LocalDate
import java.time.LocalTime

data class FiltroAgendamentoForm (
    val nome: String?,
    val data_inicio: LocalDate?,
    val data_fim: LocalDate?,
    val horario_inicio: LocalTime?,
    val horario_fim: LocalTime?,
    val assunto: String?
)