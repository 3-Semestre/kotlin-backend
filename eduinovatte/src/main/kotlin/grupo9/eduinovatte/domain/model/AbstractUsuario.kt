package grupo9.eduinovatte.domain.model

import com.fasterxml.jackson.annotation.JsonFormat
import grupo9.eduinovatte.domain.model.entity.NivelAcesso
import grupo9.eduinovatte.domain.model.entity.Situacao
import grupo9.eduinovatte.domain.model.entity.Usuario
import grupo9.eduinovatte.model.enums.SituacaoNome
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Past
import jakarta.validation.constraints.Size
import org.hibernate.validator.constraints.br.CPF
import java.time.LocalDate


abstract class AbstractUsuario (
    @field:NotNull
    val nomeCompleto: String?,
    @field:NotNull
    @field:CPF
    val cpf: String?,
    @field:Size(min = 11)
    @field:NotNull
    val telefone: String?,
    @field:Past
    @field:NotNull
    @field:JsonFormat(pattern = "yyyy-MM-dd")
    var dataNascimento: LocalDate?,
    val dataCadastro: LocalDate? = LocalDate.now(),
    val autenticado: Boolean? = false,
    @field:Email
    @field:NotNull
    val email: String?,
    val senha: String?,
    val profissao :String?,
    val nivelAcesso: NivelAcesso?,
    val situacao: Situacao? = Situacao(id = 1, SituacaoNome.ATIVO)
) {
    companion object {
        fun fromAbstractUsuario(abstractUsuario: AbstractUsuario): Usuario {
            return Usuario(
                nomeCompleto = abstractUsuario.nomeCompleto,
                cpf = abstractUsuario.cpf,
                telefone = abstractUsuario.telefone,
                dataNascimento = abstractUsuario.dataNascimento,
                email = abstractUsuario.email,
                senha = abstractUsuario.senha,
                profissao = abstractUsuario.profissao,
                nivelAcesso = abstractUsuario.nivelAcesso,
                situacao = abstractUsuario.situacao
            )
        }
    }
}

