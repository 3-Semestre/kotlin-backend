package grupo9.eduinovatte.domain.service.impl

import grupo9.eduinovatte.application.dto.response.UsuarioResponse
import grupo9.eduinovatte.domain.repository.UsuarioPerfilAlunoViewProjection
import grupo9.eduinovatte.domain.repository.UsuarioPerfilViewProjection
import grupo9.eduinovatte.domain.model.entity.HorarioProfessor
import grupo9.eduinovatte.domain.model.entity.NivelAcesso
import grupo9.eduinovatte.domain.model.entity.Situacao
import grupo9.eduinovatte.domain.model.entity.Usuario
import grupo9.eduinovatte.domain.service.HorarioProfessorService
import grupo9.eduinovatte.domain.service.SituacaoService
import grupo9.eduinovatte.domain.service.UsuarioService
import grupo9.eduinovatte.model.enums.NivelAcessoNome
import grupo9.eduinovatte.model.enums.SituacaoNome
import grupo9.eduinovatte.service.UsuarioRepository
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class UsuarioServiceImpl(
    val usuarioRepository: UsuarioRepository,
    val nivelAcessoService: NivelAcessoServiceImpl,
    val situacaoService: SituacaoService,
    val horarioProfessorService: HorarioProfessorService
): UsuarioService {
    override fun autenticar(id: Int): UsuarioResponse {
        usuarioRepository.autenticar(id)
        return retornaUsuario(usuarioRepository.findById(id).get())
    }

    override fun desautenticar(id: Int): UsuarioResponse {
        usuarioRepository.desautenticar(id)
        return retornaUsuario(usuarioRepository.findById(id).get())
    }


    override fun buscaUsuarios(tipoAcesso: NivelAcessoNome?): List<UsuarioResponse> {
        val usuarios = usuarioRepository.findByNivelAcessoNome(tipoAcesso)
        validarLista(usuarios)
        val usuariosResponse = retornaListaUsuario(usuarios)
        return usuariosResponse
    }

    override fun salvaUsuario(novoUsuario: Usuario): UsuarioResponse {
        val usuarioExistente = usuarioRepository.findByCpf(novoUsuario.cpf!!).isPresent
        if (usuarioExistente) {
            throw ResponseStatusException(HttpStatusCode.valueOf(409)) // Status 409 Conflict
        }

        val usuarios = usuarioRepository.save(novoUsuario)
        horarioProfessorService.salvar(HorarioProfessor(usuario = novoUsuario))
        val usuarioResponse = retornaUsuario(usuarios)
        return usuarioResponse
    }

    override fun editaUsuario(novoUsuario: Usuario): UsuarioResponse {

        val usuario = usuarioRepository.save(novoUsuario)
        val usuarioResponse = retornaUsuario(usuario)
        return usuarioResponse
    }

    override fun deletaUsuario(id: Int) {
        usuarioRepository.deleteById(id)
    }

    override fun desativaUsuario(id: Int): Int {
        return usuarioRepository.desativar(id)
    }


    override fun retornaListaUsuario(usuarios: List<Usuario>): List<UsuarioResponse> {
        val dtos = usuarios.map {
            UsuarioResponse.from(it)
        }
        return dtos
    }

    override fun retornaUsuario(usuario: Usuario): UsuarioResponse {
        val dto = UsuarioResponse.from(usuario)

        return dto
    }

    override fun validarLista(lista: List<*>) {
        if (lista.isEmpty()) {
            throw ResponseStatusException(HttpStatusCode.valueOf(204))
        }
    }

    override fun buscaNivelAcesso(id: Int): NivelAcesso {
        return nivelAcessoService.buscaPorId(id)
    }

    override fun buscaSituacao(id: Int?): Situacao? {
        if (id !== null) return situacaoService.buscaPorId(id)
        return null
    }

    override fun exibirPerfil(id: Int): UsuarioPerfilViewProjection? {
        val perfil = usuarioRepository.exibirPerfil(id);
        if (perfil != null) {
            if (perfil.getNivel_acesso_id().toInt() == 1) {
                throw ResponseStatusException(HttpStatus.FORBIDDEN )
            }
        }

        return perfil;
    }

    override fun exibirPerfilAluno(id: Int): UsuarioPerfilAlunoViewProjection? {
        val perfil = usuarioRepository.exibirPerfilAluno(id);
        if (perfil != null) {
            if (perfil.getNivel_acesso_id().toInt() != 1) {
                throw ResponseStatusException(HttpStatus.FORBIDDEN )
            }
        }
        return perfil;
    }

}