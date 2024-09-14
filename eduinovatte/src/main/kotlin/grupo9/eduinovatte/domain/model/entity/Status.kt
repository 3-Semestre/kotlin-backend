package grupo9.eduinovatte.domain.model.entity

import grupo9.eduinovatte.domain.model.enums.StatusNome
import jakarta.persistence.*

@Entity
data class Status(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,
    @Enumerated(EnumType.STRING)
    var nome: StatusNome?,
    var descricao: String? = nome?.descricao,
)
