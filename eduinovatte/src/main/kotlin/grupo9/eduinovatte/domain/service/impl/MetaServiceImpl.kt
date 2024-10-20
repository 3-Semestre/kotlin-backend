package grupo9.eduinovatte.domain.service.impl

import grupo9.eduinovatte.domain.model.entity.Meta
import grupo9.eduinovatte.domain.repository.MetaRepository
import grupo9.eduinovatte.domain.service.MetaService
import org.springframework.http.HttpStatusCode
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.util.*

@Service
class MetaServiceImpl(
    private val metaRepository: MetaRepository
) : MetaService {

    override fun salvar(novaMeta: Meta): Meta {
        return metaRepository.save(novaMeta)
    }

    override fun removerPorProfessor(id: Int): Int {
        return metaRepository.deletaUsuarioPeloId(id)
    }

    override fun buscaPorProfessor(id: Int): Optional<List<Meta>> {
        return metaRepository.findByUsuarioId(id)
    }

    override fun buscaMetas(): List<Meta> {
        return metaRepository.findAll()
    }

    override fun deleta(id: Int) {
        if (!metaRepository.existsById(id)) {
            throw ResponseStatusException(HttpStatusCode.valueOf(404), "Usuário nicho não encontrado")
        }
        metaRepository.deleteById(id)
    }
}
