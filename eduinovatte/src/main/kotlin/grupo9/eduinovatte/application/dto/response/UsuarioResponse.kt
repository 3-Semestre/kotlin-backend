package grupo9.eduinovatte.application.dto.response

import grupo9.eduinovatte.model.NivelAcesso
import grupo9.eduinovatte.model.Situacao
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.Size
import org.hibernate.validator.constraints.br.CPF

data class UsuarioResponse (
    var id:Int? = null,
    val nomeCompleto: String,
    val cpf: String,
    val telefone: String?,
    val autenticado: Boolean?,
    val email: String,
    val profissao :String?,
    val nivelAcesso: NivelAcesso,
    val situacao: Situacao?
)