package grupo9.eduinovatte.application.dto.requestg

import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.validation.constraints.NotNull
import java.time.LocalTime

data class HorarioProfessorRequest (
    @field:NotNull
    @field:JsonFormat(pattern = "HH:mm:ss")
    var inicio: LocalTime,
    @field:NotNull
    @field:JsonFormat(pattern = "HH:mm:ss")
    var fim: LocalTime,
    @field:NotNull
    @field:JsonFormat(pattern = "HH:mm:ss")
    var pausaInicio: LocalTime,
    @field:NotNull
    @field:JsonFormat(pattern = "HH:mm:ss")
    var pausaFim: LocalTime
)