package grupo9.eduinovatte.domain.service

import grupo9.eduinovatte.domain.model.entity.Situacao

interface SituacaoService {

    fun buscaSituacoes(): List<Situacao>

    fun buscaPorId(id: Int): Situacao
}
