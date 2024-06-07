package grupo9.eduinovatte.model

import grupo9.eduinovatte.model.enums.SituacaoNome
import jakarta.persistence.*
import jakarta.validation.constraints.*
import org.hibernate.validator.constraints.br.CPF
import java.time.LocalDate

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
    @field:Past
    @field:NotBlank
    val dataNascimento: LocalDate?,
    val dataCadastro: LocalDate? = LocalDate.now(),
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
