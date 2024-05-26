package grupo9.eduinovatte.model

import java.time.LocalDateTime
import java.time.LocalTime

data class HorarioProfessor(
    var inicio: LocalTime = LocalTime.of(9,0,0),
    var fim: LocalTime = LocalTime.of(18,0,0),
    var pausaInicio: LocalTime = LocalTime.of(12,0,0),
    var pausaFim: LocalTime = LocalTime.of(13,0,0),
    val usuario: Usuario
)