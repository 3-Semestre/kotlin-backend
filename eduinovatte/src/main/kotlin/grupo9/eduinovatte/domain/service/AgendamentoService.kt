package grupo9.eduinovatte.domain.service

import grupo9.eduinovatte.application.dto.request.AgendamentoCadastro
import grupo9.eduinovatte.application.dto.response.UsuarioNomeSemDetalhesResponse
import grupo9.eduinovatte.domain.repository.AgendamentoRepository
import grupo9.eduinovatte.model.Agendamento
import grupo9.eduinovatte.model.enums.NivelAcessoNome
import grupo9.eduinovatte.service.UsuarioRepository
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity

import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class AgendamentoService(
    val agendamentoRepository: AgendamentoRepository,
    val andamentoService: AndamentoService,
    val usuarioRepository: UsuarioRepository
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

    fun buscaAgendamentosUsuario(tipo: Int ,id: Int): List<Agendamento?> {
        val agendamentos = when (tipo) {
            3 -> agendamentoRepository.findAgendamentosByFkAluno(id)
            2 -> agendamentoRepository.findAgendamentosByFkProfessor(id)
            1 -> agendamentoRepository.findAgendamentosByFkProfessor(id)
            else -> throw ResponseStatusException(HttpStatusCode.valueOf(404))
        }

        val user = usuarioRepository.findById(id)

        return agendamentos
    }


    fun salvarAgendamento(novoAgendamento: Agendamento) : AgendamentoCadastro{
        val agendamento = agendamentoRepository.save(novoAgendamento)
        andamentoService.salvarHistorico(agendamento)

        val agendamentoResponse = retornaAgendamento(agendamento)

        return agendamentoResponse
    }
}