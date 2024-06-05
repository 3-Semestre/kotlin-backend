package grupo9.eduinovatte.domain.service

import grupo9.eduinovatte.application.dto.response.UsuarioResponse
import grupo9.eduinovatte.model.NivelAcesso
import grupo9.eduinovatte.model.Situacao
import grupo9.eduinovatte.model.Usuario
import grupo9.eduinovatte.model.enums.NivelAcessoNome
import grupo9.eduinovatte.model.enums.SituacaoNome
import grupo9.eduinovatte.service.UsuarioRepository
import org.springframework.http.HttpStatusCode
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class UsuarioService (
    val usuarioRepository: UsuarioRepository,
    val nivelAcessoService: NivelAcessoService,
    val situacaoService: SituacaoService
){
    fun autenticar(id: Int): UsuarioResponse{
        usuarioRepository.autenticar(id)
        return retornaUsuario(usuarioRepository.findById(id).get())
    }

    fun desautenticar(id: Int): UsuarioResponse{
        usuarioRepository.desautenticar(id)
        return retornaUsuario(usuarioRepository.findById(id).get())
    }

    fun validaSituacao(id: Int?){
        val situacao = buscaSituacao(id)

        if(situacao?.nome == SituacaoNome.INATIVO) {
            throw ResponseStatusException(HttpStatusCode.valueOf(401))
        }

    }

    fun validaNivelAcesso(id: Int?, tipoAcesso:NivelAcessoNome?){
        val nivelAcesso = buscaNivelAcesso(id!!)

        if(nivelAcesso.nome !== tipoAcesso){
            throw ResponseStatusException(HttpStatusCode.valueOf(401))
        }

    }
    fun buscaUsuarios(tipoAcesso: NivelAcessoNome?): List<UsuarioResponse>{
        val usuarios = usuarioRepository.findByNivelAcessoNome(tipoAcesso)
        validarLista(usuarios)
        val usuariosResponse = retornaListaUsuario(usuarios)
        return usuariosResponse
    }

    fun salvaUsuario(novoUsuario: Usuario): UsuarioResponse{
        val usuarioExistente = usuarioRepository.findByCpf(novoUsuario.cpf)
        if (usuarioExistente != null) {
            throw ResponseStatusException(HttpStatusCode.valueOf(409)) // Status 409 Conflict
        }
        val usuarios = usuarioRepository.save(novoUsuario)
        val usuarioResponse = retornaUsuario(usuarios)
        return usuarioResponse
    }

    fun editaUsuario(novoUsuario: Usuario): UsuarioResponse{

        val usuario = usuarioRepository.save(novoUsuario)
        val usuarioResponse = retornaUsuario(usuario)
        return usuarioResponse
    }

    fun deletaUsuario(id: Int){
        usuarioRepository.deleteById(id)
    }

    fun desativaUsuario(id: Int): Int{
        return usuarioRepository.desativar(id)
    }


    fun retornaListaUsuario(usuarios: List<Usuario>): List<UsuarioResponse>{
         val dtos = usuarios.map{
            UsuarioResponse.from(it)
        }
        return dtos
    }
    fun retornaUsuario(usuario: Usuario): UsuarioResponse{
        val dto = UsuarioResponse.from(usuario)

        return dto
    }

    fun validarLista(lista:List<*>) {
        if (lista.isEmpty()) {
            throw ResponseStatusException(HttpStatusCode.valueOf(204))
        }
    }

    fun buscaNivelAcesso(id:Int): NivelAcesso {
        return nivelAcessoService.buscaPorId(id)
    }

    fun buscaSituacao(id:Int?): Situacao?{
        if(id !== null) return situacaoService.buscaPorId(id)
        return null
    }

}