package grupo9.eduinovatte.domain.service.impl

import grupo9.eduinovatte.application.dto.request.UsuarioCompletoRequest
import grupo9.eduinovatte.application.dto.response.UsuarioResponse
import grupo9.eduinovatte.domain.model.entity.*
import grupo9.eduinovatte.domain.repository.projection.UsuarioPerfilAlunoViewProjection
import grupo9.eduinovatte.domain.repository.projection.UsuarioPerfilViewProjection
import grupo9.eduinovatte.domain.service.HorarioProfessorService
import grupo9.eduinovatte.domain.service.SituacaoService
import grupo9.eduinovatte.domain.service.UsuarioService
import grupo9.eduinovatte.model.enums.NivelAcessoNome
import grupo9.eduinovatte.service.UsuarioRepository
import jakarta.transaction.Transactional
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

@Service
class UsuarioServiceImpl(
    val usuarioRepository: UsuarioRepository,
    val nivelAcessoService: NivelAcessoServiceImpl,
    val situacaoService: SituacaoService,
    val horarioProfessorService: HorarioProfessorService,
    private val usuarioNichoService: UsuarioNichoServiceImpl,
    private val usuarioNivelInglesService: UsuarioNivelInglesServiceImpl,
    private val metaService: MetaServiceImpl
) : UsuarioService {
    override fun autenticar(id: Int): UsuarioResponse {
        usuarioRepository.autenticar(id)
        return retornaUsuario(usuarioRepository.findById(id).get())
    }

    override fun desautenticar(id: Int): UsuarioResponse {
        usuarioRepository.desautenticar(id)
        return retornaUsuario(usuarioRepository.findById(id).get())
    }


    override fun buscaUsuarios(tipoAcesso: NivelAcessoNome?): List<UsuarioResponse> {
        var usuarios = usuarioRepository.findByNivelAcessoNome(tipoAcesso)?.toMutableList() ?: mutableListOf()

        if (tipoAcesso == NivelAcessoNome.PROFESSOR_AUXILIAR) {
            val representantes = usuarioRepository.findByNivelAcessoNome(NivelAcessoNome.REPRESENTANTE_LEGAL)
            representantes?.let { usuarios.addAll(it) }
        }

        if (usuarios.isEmpty()) throw ResponseStatusException(HttpStatusCode.valueOf(204))
        validarLista(usuarios)
        val usuariosResponse = retornaListaUsuario(usuarios)
        return usuariosResponse
    }

    override fun buscaUsuariosPorAcesso(tipoAcesso: NivelAcessoNome?): List<Usuario> {
        var usuarios = usuarioRepository.findByNivelAcessoNome(tipoAcesso)?.toMutableList() ?: mutableListOf()

        if (tipoAcesso == NivelAcessoNome.PROFESSOR_AUXILIAR) {
            val representantes = usuarioRepository.findByNivelAcessoNome(NivelAcessoNome.REPRESENTANTE_LEGAL)
            representantes?.let { usuarios.addAll(it) }
        }

        if (usuarios.isEmpty()) throw ResponseStatusException(HttpStatusCode.valueOf(204))
        validarLista(usuarios)

        return usuarios
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

    @Transactional
    override fun salvaUsuario(novoUsuario: UsuarioCompletoRequest): UsuarioResponse {
        // Verifica se o usuário já existe pelo CPF
        val usuarioExistente = usuarioRepository.findByCpf(novoUsuario.cpf!!).isPresent
        if (usuarioExistente) {
            throw ResponseStatusException(HttpStatus.CONFLICT, "Usuário com este CPF já existe") // Status 409 Conflict
        }

        val usuario = novoUsuario.convertToUsuario(novoUsuario)

        val usuarioSalvo = usuarioRepository.save(usuario)

        if (usuario.nivelAcesso!!.id != 1) {
            horarioProfessorService.salvar(HorarioProfessor(usuario = usuarioSalvo))
        }

        while (novoUsuario.listaDeNichos?.isNotEmpty() == true) {
            novoUsuario.listaDeNichos.poll()?.let {
                usuarioNichoService.salvar(UsuarioNicho(usuario = usuarioSalvo, nicho = it))
            }
        }

        while (novoUsuario.listaDeNiveis?.isNotEmpty() == true) {
            novoUsuario.listaDeNiveis.poll()?.let {
                usuarioNivelInglesService.salvar(UsuarioNivelIngles(usuario = usuarioSalvo, nivelIngles = it))
            }
        }

        if (usuario.nivelAcesso!!.id != 1 && novoUsuario.meta != null) {
            val meta = Meta(usuario = usuarioSalvo, qtdAula = novoUsuario.meta)
            metaService.salvar(meta);
        }

        return retornaUsuario(usuarioSalvo)
    }

    override fun editaUsuario(novoUsuario: Usuario): UsuarioResponse {
        val usuarioExistente = usuarioRepository.findById(novoUsuario.id!!)
        if (usuarioExistente.isEmpty) {
            throw ResponseStatusException(HttpStatusCode.valueOf(404)) // Status 404 Not Found
        }
        if (novoUsuario.senha == null) {
            novoUsuario.senha = usuarioExistente.get().senha
        } else if (novoUsuario.senha != usuarioExistente.get().senha) {
            throw ResponseStatusException(HttpStatusCode.valueOf(401))
        }

        novoUsuario.autenticado = usuarioExistente.get().autenticado;
        novoUsuario.dataCadastro = usuarioExistente.get().dataCadastro;

        val usuario = usuarioRepository.save(novoUsuario)
        val usuarioResponse = retornaUsuario(usuario)
        return usuarioResponse
    }

    override fun deletaUsuario(id: Int) {
        usuarioRepository.deleteById(id)
    }

    override fun atualizaStatusUsuario(id: Int, status: Int): Int {
        require(status == 1 || status == 2) { "Status inválido." }
        return usuarioRepository.atualizarStatusUsuario(id, status)
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
                throw ResponseStatusException(HttpStatus.FORBIDDEN)
            }
        }

        return perfil;
    }

    override fun exibirPerfilAluno(id: Int): UsuarioPerfilAlunoViewProjection? {
        val perfil = usuarioRepository.exibirPerfilAluno(id);
        if (perfil != null) {
            if (perfil.getNivel_acesso_id().toInt() != 1) {
                throw ResponseStatusException(HttpStatus.FORBIDDEN)
            }
        }
        return perfil;
    }

    override fun exibirAlunos(
        pageable: Pageable,
        nome: String?,
        cpf: String?,
        nicho: String?,
        nivelIngles: String?,
        situacao: String?
    ): Page<UsuarioPerfilAlunoViewProjection> {
        return usuarioRepository.exibirAlunos(pageable, nome, cpf, nicho, nivelIngles, situacao)
    }

    override fun exibirProfessores(
        pageable: Pageable,
        nome: String?,
        cpf: String?,
        nicho: String?,
        nivelIngles: String?,
        situacao: String?
    ): Page<UsuarioPerfilViewProjection> {
        return usuarioRepository.exibirProfessores(pageable, nome, cpf, nicho, nivelIngles, situacao)
    }
}