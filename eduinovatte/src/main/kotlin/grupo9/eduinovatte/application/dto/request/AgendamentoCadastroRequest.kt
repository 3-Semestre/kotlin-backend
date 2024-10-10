package grupo9.eduinovatte.application.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.time.LocalDate
import java.time.LocalTime

data class AgendamentoCadastroRequest (
    @NotNull
    var data: LocalDate,

    @NotNull
    var horarioInicio: LocalTime,

    @NotNull
    var horarioFim: LocalTime,

    @NotNull
    var fk_professor: Int,

    @NotNull
    var fk_aluno: Int
)
