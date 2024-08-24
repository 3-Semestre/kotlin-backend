package grupo9.eduinovatte.domain.service

import grupo9.eduinovatte.domain.model.UsuarioNivelIngles
import grupo9.eduinovatte.domain.repository.UsuarioNivelInglesRepository
import org.springframework.stereotype.Service

@Service
class UsuarioNivelInglesService (
    val usuarioNivelInglesRepository : UsuarioNivelInglesRepository
) {

    fun salvar(novoUsuarioNivelIngles: UsuarioNivelIngles): UsuarioNivelIngles {
        val usuarioNivelIngles = usuarioNivelInglesRepository.save(novoUsuarioNivelIngles)

        return usuarioNivelIngles
    }

    fun buscaPorNivelIngles(id: Int): List<UsuarioNivelIngles> {
        val usuarioNivelIngles = usuarioNivelInglesRepository.findByNivelInglesId(id)

        return usuarioNivelIngles
    }

    fun buscaNiveisIngles(): List<UsuarioNivelIngles> {
        val usuarioNivelIngles = usuarioNivelInglesRepository.findAll()

        return usuarioNivelIngles
    }

    fun buscaPorIdUsuario(id: Int): List<UsuarioNivelIngles> {
        val usuarioNivelIngles = usuarioNivelInglesRepository.findByUsuarioId(id)

        return usuarioNivelIngles
    }
    fun deleta(id: Int) {
        usuarioNivelInglesRepository.deleteById(id)
    }
}