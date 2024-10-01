package grupo9.eduinovatte.domain.model.entity

import grupo9.eduinovatte.model.enums.NichoNome
import jakarta.persistence.*

@Entity
data class Nicho (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int,
    @Enumerated(EnumType.STRING)
    val nome: NichoNome?
)