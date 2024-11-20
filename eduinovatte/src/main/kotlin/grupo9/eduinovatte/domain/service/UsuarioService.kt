package grupo9.eduinovatte.domain.service

import grupo9.eduinovatte.application.dto.request.UsuarioCompletoRequest
import grupo9.eduinovatte.application.dto.response.UsuarioResponse
import grupo9.eduinovatte.domain.model.entity.NivelAcesso
import grupo9.eduinovatte.domain.model.entity.Situacao
import grupo9.eduinovatte.domain.model.entity.Usuario
import grupo9.eduinovatte.domain.repository.projection.UsuarioPerfilAlunoViewProjection
import grupo9.eduinovatte.domain.repository.projection.UsuarioPerfilViewProjection
import grupo9.eduinovatte.model.enums.NivelAcessoNome
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface UsuarioService {
    fun autenticar(id: Int): UsuarioResponse
    fun desautenticar(id: Int): UsuarioResponse
    fun buscaUsuarios(tipoAcesso: NivelAcessoNome?): List<UsuarioResponse>
    fun salvaUsuario(novoUsuario: Usuario): UsuarioResponse
    fun salvaUsuario(novoUsuario: UsuarioCompletoRequest): UsuarioResponse
    fun editaUsuario(novoUsuario: Usuario): UsuarioResponse
    fun deletaUsuario(id: Int)
    fun desativaUsuario(id: Int): Int
    fun retornaListaUsuario(usuarios: List<Usuario>): List<UsuarioResponse>
    fun retornaUsuario(usuario: Usuario): UsuarioResponse
    fun validarLista(lista: List<*>)
    fun buscaNivelAcesso(id: Int): NivelAcesso
    fun buscaSituacao(id: Int?): Situacao?
    fun exibirPerfil(id: Int): UsuarioPerfilViewProjection?
    fun exibirPerfilAluno(id: Int): UsuarioPerfilAlunoViewProjection?
    fun exibirAlunos(pageable: Pageable, nome: String?, cpf: String?, nicho: String?, nivelIngles: String?, situacao: String?): Page<UsuarioPerfilAlunoViewProjection>
    fun exibirProfessores(pageable: Pageable, nome: String?, cpf: String?, nicho: String?, nivelIngles: String?, situacao: String?): Page<UsuarioPerfilViewProjection>
}
