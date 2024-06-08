package grupo9.eduinovatte.domain.service

import grupo9.eduinovatte.application.dto.request.AlterarStatus
import grupo9.eduinovatte.domain.model.Andamento
import grupo9.eduinovatte.domain.model.Status
import grupo9.eduinovatte.domain.model.enums.StatusNome
import grupo9.eduinovatte.domain.repository.AndamentoRepository
import grupo9.eduinovatte.model.Agendamento
import org.springframework.stereotype.Service
import java.time.LocalDateTime


@Service
class AndamentoService(
    val andamentoRepository: AndamentoRepository
){
    fun buscaHistorico(): List<Andamento>{
        return andamentoRepository.findAll()
    }

    fun validarStatus(antigoHistorico: Andamento, novoHistorico: Andamento){
        var antigoStatus = antigoHistorico.status!!.nome!!
        var novoStatus = novoHistorico.status!!.nome!!

        if (antigoStatus == StatusNome.CANCELADO && novoStatus != StatusNome.CANCELADO) {
            throw IllegalStateException("Não é possível mudar um agendamento cancelado para outro status")
        }

        if (antigoStatus == StatusNome.CONCLUIDO) {
            throw IllegalStateException("Não é possível mudar um agendamento concluído para outro status")
        }
    }

    fun buscaHistoricoPorId(id: Int): Andamento{
        return andamentoRepository.findById(id).get()
    }

    fun salvarHistorico(agendamento: Agendamento) : Andamento{
        val novoHistorico = Andamento()
        novoHistorico.dataAtualizacao = LocalDateTime.now()
        novoHistorico.agendamento = agendamento;

        val status = Status(id = 2, nome = StatusNome.PENDENTE)
        novoHistorico.status = status

        return andamentoRepository.save(novoHistorico)
    }

    fun mudarHistorico(novoHistoricoRequest: AlterarStatus): Andamento {
        val antigoHistorico = andamentoRepository.findTopByAgendamentoIdOrderByDataAtualizacaoDesc(novoHistoricoRequest.novoAgendamento.id!!)

        val novoHistorico = Andamento()
        novoHistorico.dataAtualizacao = LocalDateTime.now()
        novoHistorico.agendamento = novoHistoricoRequest.novoAgendamento

        val status = novoHistoricoRequest.status
        novoHistorico.status = Status(status.id, status.nome)

        validarStatus(antigoHistorico, novoHistorico)

        return andamentoRepository.save(novoHistorico)
    }

}