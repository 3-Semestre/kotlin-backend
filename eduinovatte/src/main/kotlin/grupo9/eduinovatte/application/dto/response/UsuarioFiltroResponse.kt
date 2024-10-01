package grupo9.eduinovatte.application.dto.response

import grupo9.eduinovatte.domain.model.entity.*
import java.time.LocalDate

data class UsuarioFiltroResponse (
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
    val situacao: Situacao?,
    val token: String?,
    val nicho: List<UsuarioNicho>,
    val nivelIngles: List<UsuarioNivelIngles>
    ){
    companion object {
        fun from(usuario: Usuario, nicho: List<UsuarioNicho>, nivelIngles: List<UsuarioNivelIngles>): UsuarioFiltroResponse {
            return UsuarioFiltroResponse(
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
                token = null,
                nicho = nicho,
                nivelIngles = nivelIngles
            )
        }
    }
}