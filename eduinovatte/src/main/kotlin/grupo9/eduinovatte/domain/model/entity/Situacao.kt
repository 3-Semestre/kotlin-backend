package grupo9.eduinovatte.domain.model.entity

import grupo9.eduinovatte.model.enums.NivelAcessoNome
import grupo9.eduinovatte.model.enums.SituacaoNome
import jakarta.persistence.*

@Entity
data class Situacao(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int,
    @Enumerated(EnumType.STRING)
    val nome: SituacaoNome?
)