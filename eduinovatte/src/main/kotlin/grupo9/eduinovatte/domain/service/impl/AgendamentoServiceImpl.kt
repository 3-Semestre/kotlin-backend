package grupo9.eduinovatte.domain.service.impl

import grupo9.eduinovatte.application.dto.request.AgendamentoCadastro
import grupo9.eduinovatte.domain.model.entity.Agendamento
import grupo9.eduinovatte.domain.repository.AgendamentoRepository
import grupo9.eduinovatte.model.enums.NivelAcessoNome
import grupo9.eduinovatte.domain.service.AgendamentoService
import grupo9.eduinovatte.service.UsuarioRepository
import org.springframework.http.HttpStatusCode
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

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

    override fun buscaAgendamentoPorId(id: Int): Agendamento {
        return agendamentoRepository.findById(id).get()
    }

    override fun buscaAgendamentosUsuario(tipo: Int, id: Int): List<Agendamento?> {
        val agendamentos = when (tipo) {
            1 -> agendamentoRepository.findAgendamentosByFkAluno(id)
            2 -> agendamentoRepository.findAgendamentosByFkProfessor(id)
            3 -> agendamentoRepository.findAgendamentosByFkProfessor(id)
            else -> throw ResponseStatusException(HttpStatusCode.valueOf(404))
        }

        val user = usuarioRepository.findById(id)

        return agendamentos
    }

    override fun salvarAgendamento(novoAgendamento: Agendamento): AgendamentoCadastro {
        val agendamento = agendamentoRepository.save(novoAgendamento)
        andamentoService.salvarHistorico(agendamento)

        return retornaAgendamento(agendamento)
    }
}
