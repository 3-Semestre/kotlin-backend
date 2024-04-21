package grupo9.eduinovatte.model

import grupo9.eduinovatte.model.enums.NivelAcessoNome
import jakarta.persistence.*

@Entity
data class NivelAcesso(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int,
    @Enumerated(EnumType.STRING)
    val nome: NivelAcessoNome?
)