package grupo9.eduinovatte.domain.integration

import grupo9.eduinovatte.application.dto.response.*
import grupo9.eduinovatte.domain.model.Andamento
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import java.time.LocalDate

@FeignClient(name = "dashboard", url = "http://localhost:7000/dashboard")
interface DashboardGateway {

    @GetMapping("/{tipo}/{dataCorte}")
    abstract fun buscaUsuarioNovoPorDataCorte(@PathVariable tipo: String, @PathVariable dataCorte: LocalDate): List<UsuarioResponse>
    @GetMapping("/{tipo}/mes")
    abstract fun buscaUsuarioNovoPorMes(@PathVariable tipo: String): List<UsuarioResponse>
    @GetMapping("/{tipo}/ano")
    abstract fun buscaUsuarioNovoPorAno(@PathVariable tipo: String): List<UsuarioResponse>


    @GetMapping("/dashboard/ultimos-3-agendamentos-professor")
    fun buscarProximos3AgendamentosProfessor(): List<AgendamentoAlunoProjection>

    @GetMapping("/dashboard/qtd-agendamento-mes-professor")
    fun qtdAgendamentoMes(): Int

    @GetMapping("/dashboard/tempo-confirmacao")
    fun tempoConfirmacao(): Int

    @GetMapping("/dashboard/qtd-novos-alunos-mes")
    fun qtdNovosAlunosMes(): Int

    @GetMapping("/dashboard/qtd-cancelamento-alunos")
    fun qtdCancelamentoMes(): Int

    @GetMapping("/dashboard/qtd-conclusao")
    fun qtdConclusaoOuNao(): List<AgendamentoConclusaoOuNaoProjection>

    @GetMapping("/dashboard/taxa-cancelamento")
    fun taxaCancelamento(): Float

    @GetMapping("/dashboard/proximos-agendamentos-professor")
    fun proximosAgendamentos(): List<AgendamentoProximosProjection>

    @GetMapping("/dashboard/agendamentos-passados-professor")
    fun agendamentosPassados(): List<AgendamentoProximosProjection>

    @GetMapping("/dashboard/todos-professores")
    fun todosProfessores(): List<UsuarioResponse>

    @GetMapping("/dashboard/todos-alunos")
    fun todosAlunos(): List<UsuarioResponse>

    @GetMapping("/dashboard/ultimos-3-agendamentos-aluno")
    fun buscarProximos3AgendamentosAluno(): List<AgendamentoAlunoProjection>

    @GetMapping("/dashboard/visao-mes-aluno")
    fun visaoPorMes(): List<AgendamentoVisaoRepository>

    @GetMapping("/dashboard/top-3-meses-aluno")
    fun top3MesesAluno(): List<AgendamentoVisaoRepository>

    @GetMapping("/dashboard/listagem-agendamento-aluno")
    fun buscarListaAgendamentoAluno(): List<Andamento>

    @GetMapping("/dashboard/historico-agendamento-aluno")
    fun listaHistoricoAgendamentoAluno(): List<Andamento>

}