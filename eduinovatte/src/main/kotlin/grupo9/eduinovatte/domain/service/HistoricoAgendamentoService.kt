package grupo9.eduinovatte.domain.service

import grupo9.eduinovatte.domain.model.HistoricoAgendamento
import grupo9.eduinovatte.domain.model.Status
import grupo9.eduinovatte.domain.model.enums.StatusNome
import grupo9.eduinovatte.domain.repository.HistoricoAgendamentoRepository
import grupo9.eduinovatte.model.Agendamento
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class HistoricoAgendamentoService(
    val historicoAgendamentoRepository: HistoricoAgendamentoRepository
){
    fun buscaHistorico(): List<HistoricoAgendamento>{
        return historicoAgendamentoRepository.findAll()
    }

    fun buscaHistoricoPorId(id: Int): HistoricoAgendamento{
        return historicoAgendamentoRepository.findById(id).get()
    }

    fun salvarHistorico(agendamento: Agendamento) : HistoricoAgendamento{
        val novoHistorico = HistoricoAgendamento()
        novoHistorico.dataAtualizacao = LocalDate.now()
        novoHistorico.agendamento = agendamento;
        novoHistorico.status = Status(id = 1, StatusNome.PENDENTE)
        return historicoAgendamentoRepository.save(novoHistorico)
    }
}