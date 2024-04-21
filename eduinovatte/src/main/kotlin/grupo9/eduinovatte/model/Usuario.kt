package grupo9.eduinovatte.model

import jakarta.persistence.*
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.Size

@Entity
@Cacheable(false)
data class Usuario (
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    var id:Int,
    val nomeCompleto: String,
    @field:Size(min = 11)
    val cpf: String,
    @field:Size(min = 11)
    val telefone: String?,
    val autenticado: Boolean? = false,
    @field:Email
    val email: String?,
    val senha: String?,
    @field:ManyToOne
    val nivelAcesso: NivelAcesso
)