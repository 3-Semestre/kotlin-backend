package grupo9.eduinovatte.controller

import grupo9.eduinovatte.domain.model.entity.UsuarioNicho
import grupo9.eduinovatte.service.UsuarioNichoRepository
import org.springframework.stereotype.Service


@Service
class UsuarioNichoService(
    val usuarioNichoRepository: UsuarioNichoRepository
) {

    fun salvar(novoUsuarioNicho: UsuarioNicho): UsuarioNicho {
        val usuarioNicho = usuarioNichoRepository.save(novoUsuarioNicho)

        return usuarioNicho
    }

    fun buscaPorNicho(id: Int): List<UsuarioNicho> {
        val usuarioNichos = usuarioNichoRepository.findByNichoId(id)

        return usuarioNichos
    }

    fun buscaUsuariosNichos(): List<UsuarioNicho> {
        val usuarioNichos = usuarioNichoRepository.findAll()

        return usuarioNichos
    }

    fun buscaPorIdUsuario(id: Int): List<UsuarioNicho> {
        val usuarioNichos = usuarioNichoRepository.findByUsuarioId(id)

        return usuarioNichos
    }
    fun deleta(id: Int) {
        usuarioNichoRepository.deleteById(id)
    }

}

