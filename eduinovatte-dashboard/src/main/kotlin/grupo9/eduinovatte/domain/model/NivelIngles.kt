package grupo9.eduinovatte.model

import grupo9.eduinovatte.model.enums.NivelInglesNome
import jakarta.persistence.*

@Entity
data class NivelIngles (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int,
    @Enumerated(EnumType.STRING)
    val nome: NivelInglesNome?
)   