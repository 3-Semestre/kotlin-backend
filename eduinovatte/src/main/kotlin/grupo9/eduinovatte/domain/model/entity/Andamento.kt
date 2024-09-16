package grupo9.eduinovatte.domain.model.entity

import jakarta.persistence.*
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import java.time.LocalDateTime

@Entity(name = "historico_agendamento")
data class Andamento (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    var dataAtualizacao: LocalDateTime? = null,

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne
    var status: Status? = null,

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne
    var agendamento: Agendamento? = null
)