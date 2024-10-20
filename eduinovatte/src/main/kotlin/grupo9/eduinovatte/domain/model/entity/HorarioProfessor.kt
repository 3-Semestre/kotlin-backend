package grupo9.eduinovatte.domain.model.entity

import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
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
    @OnDelete(action = OnDeleteAction.CASCADE)
    @field:ManyToOne
    val usuario: Usuario
){
}
