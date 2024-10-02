package grupo9.eduinovatte.domain.service.impl

import grupo9.eduinovatte.domain.model.entity.UsuarioNicho
import grupo9.eduinovatte.domain.service.UsuarioNichoService
import grupo9.eduinovatte.service.UsuarioNichoRepository
import org.springframework.http.HttpStatusCode
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class UsuarioNichoServiceImpl(
    private val usuarioNichoRepository: UsuarioNichoRepository
) : UsuarioNichoService {

    override fun salvar(novoUsuarioNicho: UsuarioNicho): UsuarioNicho {
        return usuarioNichoRepository.save(novoUsuarioNicho)
    }

    override fun removerPorUsuario(id: Int): Int {
        return usuarioNichoRepository.deletaUsuarioPeloId(id)
    }

    override fun buscaPorNicho(id: Int): List<UsuarioNicho> {
        return usuarioNichoRepository.findByNichoId(id)
    }

    override fun buscaUsuariosNichos(): List<UsuarioNicho> {
        return usuarioNichoRepository.findAll()
    }

    override fun buscaPorIdUsuario(id: Int): List<UsuarioNicho> {
        return usuarioNichoRepository.findByUsuarioId(id)
    }

    override fun deleta(id: Int) {
        if (!usuarioNichoRepository.existsById(id)) {
            throw ResponseStatusException(HttpStatusCode.valueOf(404), "Usuário nicho não encontrado")
        }
        usuarioNichoRepository.deleteById(id)
    }
}
