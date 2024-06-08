package grupo9.eduinovatte.domain.model

import grupo9.eduinovatte.model.Agendamento
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
data class Andamento (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    var dataAtualizacao: LocalDateTime? = null,

    @ManyToOne
    @JoinColumn(name = "fk_status")
    var status: Status? = null,

    @ManyToOne
    @JoinColumn(name = "fk_agendamento")
    var agendamento: Agendamento? = null
)