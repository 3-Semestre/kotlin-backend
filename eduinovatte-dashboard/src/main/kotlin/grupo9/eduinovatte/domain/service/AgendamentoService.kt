package grupo9.eduinovatte.domain.service

import grupo9.eduinovatte.application.dto.request.AgendamentoCadastro
import grupo9.eduinovatte.application.dto.response.*
import grupo9.eduinovatte.domain.model.Andamento
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

    fun buscarUltimos3AgendamentosProfessor(): List<AgendamentoAlunoProjection>? {
        val agendamento = agendamentoRepository.buscarUltimos3AgendamentosProfessor()

        if(agendamento!!.isEmpty()){
            throw ResponseStatusException(HttpStatusCode.valueOf(204))
        }

        return agendamento
    }

    fun qtdAgendamentoMes(): Int {
        val agendamento = agendamentoRepository.qtdAgendamentoMesProfessor()
            ?: throw ResponseStatusException(HttpStatusCode.valueOf(204))

        return agendamento
    }

    fun tempoConfirmacao(): Int? {
        val agendamento = agendamentoRepository.qtdAgendamentoMesProfessor()
            ?: throw ResponseStatusException(HttpStatusCode.valueOf(204))

        return agendamento

    }

    fun qtdNovosAlunosMes(): Int {
        val agendamento = agendamentoRepository.qtdAlunosNovosProfessor()
            ?: throw ResponseStatusException(HttpStatusCode.valueOf(204))

        return agendamento
    }

    fun qtdCancelamentoMes(): Int? {
        val agendamento = agendamentoRepository.qtdCancelamentoAulasProfessor()
            ?: throw ResponseStatusException(HttpStatusCode.valueOf(204))

        return agendamento
    }

    fun qtdConclusaoOuNao(): List<AgendamentoConclusaoOuNaoProjection>? {
        val agendamento = agendamentoRepository.qtdConclusaoProfessor()
            ?: throw ResponseStatusException(HttpStatusCode.valueOf(204))

        return agendamento
    }

    fun taxaCancelamento(): Float? {
        val cancelamento = agendamentoRepository.taxaCancelamentoProfessor()
            ?: throw ResponseStatusException(HttpStatusCode.valueOf(204))

        return cancelamento
    }

    fun proximosAgendamentos(): List<AgendamentoProximosProjection>? {
        val agendamento = agendamentoRepository.proximosAgendamentosProfessor()

        if(agendamento!!.isEmpty()){
            throw ResponseStatusException(HttpStatusCode.valueOf(204))
        }

        return agendamento
    }

    fun agendamentosPassados(): List<AgendamentoProximosProjection>? {
        val agendamento = agendamentoRepository.agendamentosPassadosProfessor()

        if(agendamento!!.isEmpty()){
            throw ResponseStatusException(HttpStatusCode.valueOf(204))
        }

        return agendamento
    }

    fun buscarUltimos3AgendamentosAluno(): List<AgendamentoAlunoProjection>? {
        val agendamento = agendamentoRepository.buscarUltimos3AgendamentosAluno()

        if(agendamento!!.isEmpty()){
            throw ResponseStatusException(HttpStatusCode.valueOf(204))
        }

        return agendamento
    }

    fun visaoPorMesAluno(): List<AgendamentoVisaoRepository>? {
        val agendamento = agendamentoRepository.buscarVisaoPorMesAluno()

        if(agendamento!!.isEmpty()){
            throw ResponseStatusException(HttpStatusCode.valueOf(204))
        }

        return agendamento

    }

    fun buscarTop3MesesAluno(): List<AgendamentoVisaoRepository>? {
        val agendamento = agendamentoRepository.buscarTop3MesesAluno()

        if(agendamento!!.isEmpty()){
            throw ResponseStatusException(HttpStatusCode.valueOf(204))
        }

        return agendamento
    }

    fun buscarListaAgendamentoAluno(): List<Andamento>? {
        val agendamento = agendamentoRepository.buscarListaAgendamentoAluno()

        if(agendamento!!.isEmpty()){
            throw ResponseStatusException(HttpStatusCode.valueOf(204))
        }

        return agendamento
    }

    fun listaHistoricoAgendamentoAluno(): List<Andamento>? {
        val agendamento = agendamentoRepository.listaHistoricoAgendamentoAluno()

        if(agendamento!!.isEmpty()){
            throw ResponseStatusException(HttpStatusCode.valueOf(204))
        }

        return agendamento
    }

}