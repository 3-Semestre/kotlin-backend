package grupo9.eduinovatte.domain.service

import grupo9.eduinovatte.application.dto.request.AgendamentoCadastro
import grupo9.eduinovatte.application.dto.response.*
import grupo9.eduinovatte.domain.model.Andamento
import grupo9.eduinovatte.domain.repository.AgendamentoCancelamentoPorMesProjection
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
) {
    fun validaNivelAcesso(novoAgendamento: Agendamento) {
        val professor = novoAgendamento.professor!!
        val aluno = novoAgendamento.aluno!!
        if ((professor.nivelAcesso!!.nome == NivelAcessoNome.ALUNO) || (aluno.nivelAcesso!!.nome != NivelAcessoNome.ALUNO)) {
            throw ResponseStatusException(HttpStatusCode.valueOf(400))
        }
    }

    fun retornaAgendamento(agendamento: Agendamento): AgendamentoCadastro {
        val dto = AgendamentoCadastro.from(agendamento)

        return dto
    }

    fun buscaAgendamentos(): List<Agendamento> {
        return agendamentoRepository.findAll()
    }

    fun buscaAgendamentoPorId(id: Int): Agendamento {
        return agendamentoRepository.findById(id).get()
    }

    fun buscaAgendamentosUsuario(usuario: UsuarioNomeSemDetalhesResponse): List<Agendamento> {
        val cpf = usuario.cpf!!
        val user = usuarioRepository.findByCpf(cpf)

        return agendamentoRepository.findAgendamentosByUserId(user!!.id!!)

    }


    fun salvarAgendamento(novoAgendamento: Agendamento): AgendamentoCadastro {
        val agendamento = agendamentoRepository.save(novoAgendamento)
        andamentoService.salvarHistorico(agendamento)

        val agendamentoResponse = retornaAgendamento(agendamento)

        return agendamentoResponse
    }

    fun buscarUltimos3AgendamentosProfessor(id: Int): List<AgendamentoAlunoProjection>? {
        val agendamento = agendamentoRepository.buscarUltimos3AgendamentosProfessor(id)

        if (agendamento!!.isEmpty()) {
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

        fun taxaCancelamentoPorMes(): List<AgendamentoCancelamentoPorMesProjection> {
            val cancelamento = agendamentoRepository.taxaCancelamentoProfessorPorMes()
                ?: throw ResponseStatusException(HttpStatusCode.valueOf(204))

            return cancelamento;
        }

    fun proximosAgendamentos(id: Int): List<AgendamentoProximosProjection>? {
        val agendamento = agendamentoRepository.proximosAgendamentosProfessor(id)

        if (agendamento!!.isEmpty()) {
            throw ResponseStatusException(HttpStatusCode.valueOf(204))
        }

        return agendamento
    }

    fun proximosAgendamentosAluno(id: Int): List<AgendamentoProximosProjection>? {
        val agendamento = agendamentoRepository.proximosAgendamentosAluno(id)

        if (agendamento!!.isEmpty()) {
            throw ResponseStatusException(HttpStatusCode.valueOf(204))
        }

        return agendamento
    }

    fun agendamentosPassados(id: Int): List<AgendamentoProximosProjection>? {
        val agendamento = agendamentoRepository.agendamentosPassadosProfessor(id)

        if (agendamento!!.isEmpty()) {
            throw ResponseStatusException(HttpStatusCode.valueOf(204))
        }

        return agendamento
    }

    fun agendamentosPassadosAluno(id: Int): List<AgendamentoProximosProjection>? {
        val agendamento = agendamentoRepository.agendamentosPassadosAluno(id)

        if (agendamento!!.isEmpty()) {
            throw ResponseStatusException(HttpStatusCode.valueOf(204))
        }

        return agendamento
    }

    fun buscarUltimos3AgendamentosAluno(id: Int): List<AgendamentoAlunoProjection>? {
        val agendamento = agendamentoRepository.buscarUltimos3AgendamentosAluno(id)

        if (agendamento!!.isEmpty()) {
            throw ResponseStatusException(HttpStatusCode.valueOf(204))
        }

        return agendamento
    }

    fun visaoPorMesAluno(id: Int): List<AgendamentoVisaoRepository>? {
        val agendamento = agendamentoRepository.buscarVisaoPorMesAluno(id)

        if (agendamento!!.isEmpty()) {
            throw ResponseStatusException(HttpStatusCode.valueOf(204))
        }

        return agendamento

    }

    fun buscarTop3MesesAluno(id: Int): List<AgendamentoVisaoRepository>? {
        val agendamento = agendamentoRepository.buscarTop3MesesAluno(id)

        if (agendamento!!.isEmpty()) {
            throw ResponseStatusException(HttpStatusCode.valueOf(204))
        }

        return agendamento
    }

    fun buscarListaAgendamentoAluno(): List<Andamento>? {
        val agendamento = agendamentoRepository.buscarListaAgendamentoAluno()

        if (agendamento!!.isEmpty()) {
            throw ResponseStatusException(HttpStatusCode.valueOf(204))
        }

        return agendamento
    }

    fun listaHistoricoAgendamentoAluno(): List<Andamento>? {
        val agendamento = agendamentoRepository.listaHistoricoAgendamentoAluno()

        if (agendamento!!.isEmpty()) {
            throw ResponseStatusException(HttpStatusCode.valueOf(204))
        }

        return agendamento
    }

    fun buscaQtdAgendamentosCancelados(id: Int): Int? {
        val agendamento = agendamentoRepository.buscaQtdAgendamentosCancelados(id)

        if (agendamento == null) {
            throw ResponseStatusException(HttpStatusCode.valueOf(204))
        }

        return agendamento
    }

    fun buscaAulasTransferidasPorProfessor(id: Int): Int? {
        val agendamento = agendamentoRepository.buscaAulasTransferidasPorProfessor(id)

        if (agendamento == null) {
            throw ResponseStatusException(HttpStatusCode.valueOf(204))
        }

        return agendamento
    }

    fun buscaTaxaCumprimentoMetas(id: Int): List<TaxaCumprimentoRepository>? {
        val agendamento = agendamentoRepository.buscaTaxaCumprimentoMetas(id)

        if (agendamento == null) {
            throw ResponseStatusException(HttpStatusCode.valueOf(204))
        }

        return agendamento
    }

    fun buscaQtdAlunoPorMes(id: Int): List<AlunosQuantidadeRepository>? {
        val agendamento = agendamentoRepository.buscaQtdAlunoPorMes(id)

        if (agendamento == null) {
            throw ResponseStatusException(HttpStatusCode.valueOf(204))
        }

        return agendamento
    }

}