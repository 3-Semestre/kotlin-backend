package grupo9.eduinovatte.domain.service.impl

import grupo9.eduinovatte.domain.model.entity.UsuarioNivelIngles
import grupo9.eduinovatte.domain.repository.UsuarioNivelInglesRepository
import grupo9.eduinovatte.domain.service.UsuarioNivelInglesService
import org.springframework.stereotype.Service

@Service
class UsuarioNivelInglesServiceImpl (
    val usuarioNivelInglesRepository : UsuarioNivelInglesRepository
): UsuarioNivelInglesService {

    override fun salvar(novoUsuarioNivelIngles: UsuarioNivelIngles): UsuarioNivelIngles {
        val usuarioNivelIngles = usuarioNivelInglesRepository.save(novoUsuarioNivelIngles)

        return usuarioNivelIngles
    }

    override fun buscaPorNivelIngles(id: Int): List<UsuarioNivelIngles> {
        val usuarioNivelIngles = usuarioNivelInglesRepository.findByNivelInglesId(id)

        return usuarioNivelIngles
    }

    override fun buscaNiveisIngles(): List<UsuarioNivelIngles> {
        val usuarioNivelIngles = usuarioNivelInglesRepository.findAll()

        return usuarioNivelIngles
    }

    override fun buscaPorIdUsuario(id: Int): List<UsuarioNivelIngles> {
        val usuarioNivelIngles = usuarioNivelInglesRepository.findByUsuarioId(id)

        return usuarioNivelIngles
    }
    override fun deleta(id: Int) {
        usuarioNivelInglesRepository.deleteById(id)
    }

}