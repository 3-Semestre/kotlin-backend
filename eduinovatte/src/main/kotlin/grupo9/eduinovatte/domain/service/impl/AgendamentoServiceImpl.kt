package grupo9.eduinovatte.domain.service.impl

import grupo9.eduinovatte.application.dto.request.AgendamentoCadastro
import grupo9.eduinovatte.application.dto.request.AgendamentoCadastroRequest
import grupo9.eduinovatte.application.dto.request.FiltroAgendamentoForm
import grupo9.eduinovatte.domain.model.entity.Agendamento
import grupo9.eduinovatte.domain.repository.AgendamentoRepository
import grupo9.eduinovatte.domain.repository.AgendamentosDetalhesListagemResponse
import grupo9.eduinovatte.model.enums.NivelAcessoNome
import grupo9.eduinovatte.domain.service.AgendamentoService
import grupo9.eduinovatte.service.UsuarioRepository
import jakarta.transaction.Transactional
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatusCode
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.util.*

@Service
class AgendamentoServiceImpl(
    val agendamentoService: AgendamentoRepository,
    val andamentoService: AndamentoServiceImpl,
    val usuarioService: UsuarioRepository
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
        return agendamentoService.findAll()
    }

    override fun buscaAgendamentoPorId(id: Int): Optional<Agendamento> {
        return agendamentoService.findById(id)
    }

    override fun buscaAgendamentosUsuario(tipo: Int, id: Int, pageable: Pageable): Page<Agendamento> {
        val agendamentos = when (tipo) {
            1 -> agendamentoService.findAgendamentosByFkAluno(id, pageable)
            2 -> agendamentoService.findAgendamentosByFkProfessor(id, pageable)
            3 -> agendamentoService.findAgendamentosByFkProfessor(id, pageable)
            else -> throw ResponseStatusException(HttpStatusCode.valueOf(404))
        }

        val user = usuarioService.findById(id)

        return agendamentos
    }

    override fun buscaAgendamentosTempoUsuario(id: Int, tempo: String, pageable: Pageable): Page<AgendamentosDetalhesListagemResponse> {
        val usuario = usuarioService.findById(id).get()

        val agendamentos = when{
            usuario.nivelAcesso!!.id == 1 && tempo == "passado" -> agendamentoService.findAgendamentosPassadosByFkAluno(id, pageable)
            usuario.nivelAcesso.id != 1  && tempo == "passado" -> agendamentoService.findAgendamentosPassadosByFkProfessor(id, pageable)
            usuario.nivelAcesso.id == 1  && tempo == "futuro" -> agendamentoService.findAgendamentosFuturoByFkAluno(id, pageable)
            usuario.nivelAcesso.id != 1  && tempo == "futuro" -> agendamentoService.findAgendamentosFuturoByFkProfessor(id, pageable)
            else -> throw ResponseStatusException(HttpStatusCode.valueOf(404))
        }

        return agendamentos
    }

    @Transactional
    override fun salvarAgendamento(novoAgendamento: AgendamentoCadastroRequest): AgendamentoCadastro {
        val aluno = usuarioService.findById(novoAgendamento.fk_aluno);
        val professor = usuarioService.findById(novoAgendamento.fk_professor);
        if(aluno.isPresent && professor.isPresent){
            val agendamento_salvo = Agendamento(null,novoAgendamento.data,novoAgendamento.horarioInicio,novoAgendamento.horarioFim,"Aguardando Confirmação", professor.get(), aluno.get());
            val agendamento = agendamentoService.save(agendamento_salvo)
            andamentoService.salvarHistorico(agendamento)

            return retornaAgendamento(agendamento)
        } else{
            throw ResponseStatusException(HttpStatusCode.valueOf(404))
        }

    }


    override fun filtrarAlunoPassado(filtro: FiltroAgendamentoForm, id: Int): List<Agendamento?> {
        return agendamentoRepository.filtrarAlunoPassado(filtro.nome, filtro.data_inicio, filtro.data_fim, filtro.horario_inicio, filtro.horario_fim, id)
    }

    override fun filtrarAlunoFuturo(filtro: FiltroAgendamentoForm, id: Int): List<Agendamento?> {
        return agendamentoRepository.filtrarAlunoFuturo(filtro.nome, filtro.data_inicio, filtro.data_fim, filtro.horario_inicio, filtro.horario_fim, id)
    }

    override fun filtrarProfessorPassado(filtro: FiltroAgendamentoForm, id: Int): List<Agendamento?> {
        return agendamentoRepository.filtrarProfessorPassado(filtro.nome, filtro.data_inicio, filtro.data_fim, filtro.horario_inicio, filtro.horario_fim, filtro.assunto, id)
    }

    override fun filtrarProfessorFuturo(filtro: FiltroAgendamentoForm, id: Int): List<Agendamento?> {
        return agendamentoRepository.filtrarProfessorFuturo(filtro.nome, filtro.data_inicio, filtro.data_fim, filtro.horario_inicio, filtro.horario_fim, filtro.assunto, id)
    }
}
