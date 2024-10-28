package grupo9.eduinovatte.application.dto.request

import org.jetbrains.annotations.NotNull
import java.time.LocalDate
import java.time.LocalTime

data class HorarioTransferenciaRequest(
    @NotNull
    var data: LocalDate,

    @NotNull
    var horarioInicio: LocalTime,

    @NotNull
    var horarioFim: LocalTime
)