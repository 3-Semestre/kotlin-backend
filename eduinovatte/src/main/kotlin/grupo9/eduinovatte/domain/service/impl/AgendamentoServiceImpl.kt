package grupo9.eduinovatte.domain.service.impl

import grupo9.eduinovatte.application.dto.request.AgendamentoCadastro
import grupo9.eduinovatte.application.dto.request.FiltroAgendamentoForm
import grupo9.eduinovatte.domain.model.entity.Agendamento
import grupo9.eduinovatte.domain.repository.AgendamentoRepository
import grupo9.eduinovatte.domain.repository.AgendamentosDetalhesListagemResponse
import grupo9.eduinovatte.model.enums.NivelAcessoNome
import grupo9.eduinovatte.domain.service.AgendamentoService
import grupo9.eduinovatte.service.UsuarioRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatusCode
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDate
import java.time.LocalTime
import java.util.*

@Service
class AgendamentoServiceImpl(
    val agendamentoRepository: AgendamentoRepository,
    val andamentoService: AndamentoServiceImpl,
    val usuarioRepository: UsuarioRepository
) : AgendamentoService {

    override fun validaNivelAcesso(novoAgendamento: Agendamento) {
        val professor = novoAgendamento.professor!!
        val aluno = novoAgendamento.aluno!!
        if ((professor.nivelAcesso!!.nome == NivelAcessoNome.ALUNO) || (aluno.nivelAcesso!!.nome != NivelAcessoNome.ALUNO)) {
            throw ResponseStatusException(HttpStatusCode.valueOf(400))
        }
    }

    override fun retornaAgendamento(agendamento: Agendamento): AgendamentoCadastro {
        return AgendamentoCadastro.from(agendamento)
    }

    override fun buscaAgendamentos(): List<Agendamento> {
        return agendamentoRepository.findAll()
    }

    override fun buscaAgendamentoPorId(id: Int): Optional<Agendamento> {
        return agendamentoRepository.findById(id)
    }

    override fun buscaAgendamentosUsuario(tipo: Int, id: Int, pageable: Pageable): Page<Agendamento> {
        val agendamentos = when (tipo) {
            1 -> agendamentoRepository.findAgendamentosByFkAluno(id, pageable)
            2 -> agendamentoRepository.findAgendamentosByFkProfessor(id, pageable)
            3 -> agendamentoRepository.findAgendamentosByFkProfessor(id, pageable)
            else -> throw ResponseStatusException(HttpStatusCode.valueOf(404))
        }

        val user = usuarioRepository.findById(id)

        return agendamentos
    }

    override fun buscaAgendamentosTempoUsuario(id: Int, tempo: String, pageable: Pageable): Page<AgendamentosDetalhesListagemResponse> {
        val usuario = usuarioRepository.findById(id).get()

        val agendamentos = when{
            usuario.nivelAcesso!!.id == 1 && tempo == "passado" -> agendamentoRepository.findAgendamentosPassadosByFkAluno(id, pageable)
            usuario.nivelAcesso.id != 1  && tempo == "passado" -> agendamentoRepository.findAgendamentosPassadosByFkProfessor(id, pageable)
            usuario.nivelAcesso.id == 1  && tempo == "futuro" -> agendamentoRepository.findAgendamentosFuturoByFkAluno(id, pageable)
            usuario.nivelAcesso.id != 1  && tempo == "futuro" -> agendamentoRepository.findAgendamentosFuturoByFkProfessor(id, pageable)
            else -> throw ResponseStatusException(HttpStatusCode.valueOf(404))
        }

        return agendamentos
    }

    override fun salvarAgendamento(novoAgendamento: Agendamento): AgendamentoCadastro {
        val agendamento = agendamentoRepository.save(novoAgendamento)
        andamentoService.salvarHistorico(agendamento)

        return retornaAgendamento(agendamento)
    }


    override fun filtrarAluno(filtro: FiltroAgendamentoForm, id: Int): List<Agendamento?> {
        return agendamentoRepository.filtrarAluno(filtro.nome, filtro.data_inicio, filtro.data_fim, filtro.horario_inicio, filtro.horario_fim, id)
    }

    override fun filtrarProfessor(filtro: FiltroAgendamentoForm, id: Int): List<Agendamento?> {
        return agendamentoRepository.filtrarProfessor(filtro.nome, filtro.data_inicio, filtro.data_fim, filtro.horario_inicio, filtro.horario_fim, filtro.assunto, id)
    }
}
