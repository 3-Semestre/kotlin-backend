package grupo9.eduinovatte.domain.service

import grupo9.eduinovatte.application.dto.request.AlterarStatus
import grupo9.eduinovatte.domain.model.entity.Andamento
import grupo9.eduinovatte.domain.model.entity.Agendamento

interface AndamentoService {

    fun buscaHistorico(): List<Andamento>

    fun validarStatus(antigoHistorico: Andamento, novoHistorico: Andamento)

    fun buscaHistoricoPorId(id: Int): Andamento

    fun salvarHistorico(agendamento: Agendamento): Andamento

    fun mudarHistorico(novoHistoricoRequest: AlterarStatus): Andamento
}
