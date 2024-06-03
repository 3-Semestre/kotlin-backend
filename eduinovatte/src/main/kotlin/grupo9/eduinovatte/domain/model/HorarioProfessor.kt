package grupo9.eduinovatte.model

import jakarta.persistence.*
import java.time.LocalDateTime
import java.time.LocalTime

@Entity
data class HorarioProfessor(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int,

    @Column(name = "inicio", nullable = false)
    var inicio: LocalTime = LocalTime.of(9, 0, 0),

    @Column(name = "fim", nullable = false)
    var fim: LocalTime = LocalTime.of(18, 0, 0),

    @Column(name = "pausa_inicio", nullable = false)
    var pausaInicio: LocalTime = LocalTime.of(12, 0, 0),

    @Column(name = "pausa_fim", nullable = false)
    var pausaFim: LocalTime = LocalTime.of(13, 0, 0),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    val usuario: Usuario
)