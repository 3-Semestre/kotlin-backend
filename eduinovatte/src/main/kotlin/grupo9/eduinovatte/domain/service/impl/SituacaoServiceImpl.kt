package grupo9.eduinovatte.domain.service.impl

import grupo9.eduinovatte.domain.model.entity.Situacao
import grupo9.eduinovatte.domain.service.SituacaoService
import grupo9.eduinovatte.service.SituacaoRepository
import org.springframework.stereotype.Service

@Service
class SituacaoServiceImpl(
    val situacaoRepository: SituacaoRepository
): SituacaoService {
    override fun buscaSituacoes(): List<Situacao>{
        return situacaoRepository.findAll()
    }

    override fun buscaPorId(id: Int): Situacao {
        return situacaoRepository.findById(id).get()
    }
}