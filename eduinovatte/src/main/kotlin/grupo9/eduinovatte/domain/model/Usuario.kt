package grupo9.eduinovatte.model

import grupo9.eduinovatte.model.enums.SituacaoNome
import jakarta.persistence.*
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import org.hibernate.validator.constraints.br.CPF

@Entity
data class Usuario (
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    var id:Int? = null,
    @field:NotNull
    val nomeCompleto: String?,
    @field:NotNull
    @field:CPF
    val cpf: String?,
    @field:Size(min = 11)
    @field:NotNull
    val telefone: String?,
    val autenticado: Boolean? = false,
    @field:Email
    @field:NotNull
    val email: String?,
    val senha: String?,
    val profissao :String?,
    @field:ManyToOne
    val nivelAcesso: NivelAcesso?,
    @field:ManyToOne
    val situacao: Situacao? = Situacao(id = 2, SituacaoNome.ATIVO)
)
