package grupo9.eduinovatte.domain.service

import grupo9.eduinovatte.domain.model.entity.Situacao
import grupo9.eduinovatte.service.SituacaoRepository
import org.springframework.stereotype.Service

@Service
class SituacaoService(
    val situacaoRepository: SituacaoRepository
){
    fun buscaSituacoes(): List<Situacao>{
        return situacaoRepository.findAll()
    }

    fun buscaPorId(id: Int): Situacao {
        return situacaoRepository.findById(id).get()
    }
}