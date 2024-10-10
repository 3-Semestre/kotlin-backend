package grupo9.eduinovatte.domain.service.impl

import grupo9.eduinovatte.application.dto.request.AlterarStatus
import grupo9.eduinovatte.domain.model.entity.Andamento
import grupo9.eduinovatte.domain.model.entity.Status
import grupo9.eduinovatte.domain.model.enums.StatusNome
import grupo9.eduinovatte.domain.repository.AndamentoRepository
import grupo9.eduinovatte.domain.model.entity.Agendamento
import grupo9.eduinovatte.domain.service.AndamentoService
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class AndamentoServiceImpl(
    private val andamentoRepository: AndamentoRepository
) : AndamentoService {

    override fun buscaHistorico(): List<Andamento> {
        return andamentoRepository.findAll()
    }

    override fun validarStatus(antigoHistorico: Andamento, novoHistorico: Andamento) {
        val antigoStatus = antigoHistorico.status?.nome
        val novoStatus = novoHistorico.status?.nome

        if (antigoStatus == StatusNome.CANCELADO && novoStatus != StatusNome.CANCELADO) {
            throw IllegalStateException("Não é possível mudar um agendamento cancelado para outro status")
        }

        if (antigoStatus == StatusNome.CONCLUIDO) {
            throw IllegalStateException("Não é possível mudar um agendamento concluído para outro status")
        }
    }

    override fun buscaHistoricoPorId(id: Int): Andamento {
        return andamentoRepository.findById(id).orElseThrow {
            IllegalArgumentException("Histórico não encontrado com id $id")
        }
    }

    override fun buscaHistoricoPorIdAgendamento(id: Int): List<Andamento> {
        return andamentoRepository.findByAgendamentoIdOrderByDataAtualizacaoDesc(id)
    }

    override fun salvarHistorico(agendamento: Agendamento): Andamento {
        val novoHistorico = Andamento().apply {
            dataAtualizacao = LocalDate.now()
            this.agendamento = agendamento
            status = Status(id = 1, nome = StatusNome.PENDENTE)
        }

        return andamentoRepository.save(novoHistorico)
    }

    override fun mudarHistorico(novoHistoricoRequest: AlterarStatus): Andamento {
        val antigoHistorico = andamentoRepository.findTopByAgendamentoIdOrderByDataAtualizacaoDesc(novoHistoricoRequest.novoAgendamento.id!!)
            ?: throw IllegalArgumentException("Histórico não encontrado para o agendamento com id ${novoHistoricoRequest.novoAgendamento.id}")

        val novoHistorico = Andamento().apply {
            dataAtualizacao = LocalDate.now()
            agendamento = novoHistoricoRequest.novoAgendamento
            status = Status(novoHistoricoRequest.status.id, novoHistoricoRequest.status.nome)
        }

        validarStatus(antigoHistorico, novoHistorico)

        return andamentoRepository.save(novoHistorico)
    }
}
