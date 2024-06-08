package grupo9.eduinovatte.domain.service

import grupo9.eduinovatte.application.dto.request.AgendamentoCadastro
import grupo9.eduinovatte.domain.repository.AgendamentoRepository
import grupo9.eduinovatte.model.Agendamento
import grupo9.eduinovatte.model.enums.NivelAcessoNome
import org.springframework.http.HttpStatusCode

import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class AgendamentoService(
    val agendamentoRepository: AgendamentoRepository,
    val historicoAgendamentoService: HistoricoAgendamentoService
){
    fun validaNivelAcesso(novoAgendamento: Agendamento) {
        val professor = novoAgendamento.professor!!
        val aluno = novoAgendamento.aluno!!
        if ((professor.nivelAcesso!!.nome == NivelAcessoNome.ALUNO) || (aluno.nivelAcesso!!.nome != NivelAcessoNome.ALUNO)) {
            throw ResponseStatusException(HttpStatusCode.valueOf(400))
        }
    }

    fun retornaAgendamento(agendamento: Agendamento): AgendamentoCadastro{
        val dto = AgendamentoCadastro.from(agendamento)

        return dto
    }

    fun buscaAgendamentos(): List<Agendamento>{
        return agendamentoRepository.findAll()
    }

    fun buscaAgendamentoPorId(id: Int): Agendamento{
        return agendamentoRepository.findById(id).get()
    }

    fun salvarAgendamento(novoAgendamento: Agendamento) : AgendamentoCadastro{
        val agendamentoExistente = agendamentoRepository.findById(novoAgendamento.id!!)

        if (agendamentoExistente != null) {
            throw ResponseStatusException(HttpStatusCode.valueOf(409)) // Status 409 Conflict
        }

        validaNivelAcesso(novoAgendamento)

        val agendamento = agendamentoRepository.save(novoAgendamento)
        historicoAgendamentoService.salvarHistorico(agendamento)

        val agendamentoResponse = retornaAgendamento(agendamento)

        return agendamentoResponse
    }
}