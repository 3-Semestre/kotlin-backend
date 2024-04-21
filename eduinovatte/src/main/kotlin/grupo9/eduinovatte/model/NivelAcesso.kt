package grupo9.eduinovatte.model

import jakarta.persistence.*

@Entity
data class NivelAcesso(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int,
    @Enumerated(EnumType.STRING)
    val nome: Nivel?,
)