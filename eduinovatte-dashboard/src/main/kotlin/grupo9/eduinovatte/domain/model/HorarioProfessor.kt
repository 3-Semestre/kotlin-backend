package grupo9.eduinovatte.model

import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import java.time.LocalTime

@Entity
data class HorarioProfessor(
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,
    @field:NotNull
    @field:JsonFormat(pattern = "HH:mm:ss")
    var inicio: LocalTime = LocalTime.of(9, 0, 0),
    @field:NotNull
    @field:JsonFormat(pattern = "HH:mm:ss")
    var fim: LocalTime = LocalTime.of(18, 0, 0),
    @field:NotNull
    @field:JsonFormat(pattern = "HH:mm:ss")
    var pausaInicio: LocalTime = LocalTime.of(12, 0, 0),
    @field:NotNull
    @field:JsonFormat(pattern = "HH:mm:ss")
    var pausaFim: LocalTime = LocalTime.of(13, 0, 0),
    @field:ManyToOne
    val usuario: Usuario
)
