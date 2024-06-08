package grupo9.eduinovatte.application.dto.response

import grupo9.eduinovatte.model.NivelAcesso
import grupo9.eduinovatte.model.Situacao
import grupo9.eduinovatte.model.Usuario
import java.time.LocalDate

data class UsuarioResponse (
    var id:Int? = null,
    val nomeCompleto: String,
    val cpf: String,
    val telefone: String?,
    val autenticado: Boolean?,
    val dataNasc: LocalDate?,
    val dataCadastro: LocalDate?,
    val email: String,
    val profissao :String?,
    val nivelAcesso: NivelAcesso,
    val situacao: Situacao?
){
    companion object {
        fun from(usuario: Usuario): UsuarioResponse {
            return UsuarioResponse(
                id = usuario.id,
                nomeCompleto = usuario.nomeCompleto!!,
                cpf = usuario.cpf!!,
                telefone = usuario.telefone,
                autenticado = usuario.autenticado,
                email = usuario.email!!,
                profissao = usuario.profissao,
                nivelAcesso = usuario.nivelAcesso!!,
                situacao = usuario.situacao,
                dataNasc = usuario.dataNascimento,
                dataCadastro = usuario.dataCadastro,
            )
        }
    }
}