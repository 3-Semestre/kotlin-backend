package grupo9.eduinovatte.domain.service

import grupo9.eduinovatte.application.dto.request.AgendamentoCadastro
import grupo9.eduinovatte.application.dto.response.UsuarioNomeSemDetalhesResponse
import grupo9.eduinovatte.domain.repository.AgendamentoRepository
import grupo9.eduinovatte.model.Agendamento
import grupo9.eduinovatte.model.enums.NivelAcessoNome
import grupo9.eduinovatte.service.UsuarioRepository
import org.springframework.http.HttpStatusCode

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

    fun buscaAgendamentosUsuario(usuario: UsuarioNomeSemDetalhesResponse): List<Agendamento> {
        val cpf = usuario.cpf!!
        val user = usuarioRepository.findByCpf(cpf)

        return agendamentoRepository.findAgendamentosByUserId(user!!.id!!)

    }


    fun salvarAgendamento(novoAgendamento: Agendamento) : AgendamentoCadastro{
        val agendamento = agendamentoRepository.save(novoAgendamento)
        andamentoService.salvarHistorico(agendamento)

        val agendamentoResponse = retornaAgendamento(agendamento)

        return agendamentoResponse
    }
}