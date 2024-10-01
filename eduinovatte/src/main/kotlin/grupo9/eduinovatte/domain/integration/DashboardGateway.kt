package grupo9.eduinovatte.domain.integration

import grupo9.eduinovatte.application.dto.response.*
import grupo9.eduinovatte.domain.model.entity.Andamento
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


    @GetMapping("/ultimos-3-agendamentos-professor/{id}")
    fun buscarProximos3AgendamentosProfessor(@PathVariable("id") id: Int): List<AgendamentoAlunoProjection>

    @GetMapping("/qtd-agendamento-mes-professor/{id}")
    fun qtdAgendamentoMes(@PathVariable("id") id: Int): Int

    @GetMapping("/tempo-confirmacao")
    fun tempoConfirmacao(): Int

    @GetMapping("/qtd-novos-alunos-mes")
    fun qtdNovosAlunosMes(): Int

    @GetMapping("/qtd-cancelamento-alunos")
    fun qtdCancelamentoMes(): Int

    @GetMapping("/qtd-conclusao")
    fun qtdConclusaoOuNao(): List<AgendamentoConclusaoOuNaoProjection>

    @GetMapping("/taxa-cancelamento")
    fun taxaCancelamento(): Float

    @GetMapping("/proximos-agendamentos-professor/{id}")
    fun proximosAgendamentos(@PathVariable("id") id: Int): List<AgendamentoProximosProjection>

    @GetMapping("/proximos-agendamentos-aluno/{id}")
    fun proximosAgendamentosAluno(@PathVariable("id") id: Int): List<AgendamentoProximosProjection>
    @GetMapping("/agendamentos-passados-professor/{id}")
    fun agendamentosPassados(@PathVariable("id") id: Int): List<AgendamentoProximosProjection>

    @GetMapping("/todos-professores")
    fun todosProfessores(): List<UsuarioResponse>

    @GetMapping("/todos-alunos")
    fun todosAlunos(): List<UsuarioResponse>

    @GetMapping("/ultimos-3-agendamentos-aluno/{id}")
    fun buscarProximos3AgendamentosAluno(@PathVariable("id") id: Int): List<AgendamentoAlunoProjection>

    @GetMapping("/visao-mes-aluno/{id}")
    fun visaoPorMes(@PathVariable("id") id: Int): List<AgendamentoVisaoRepository>

    @GetMapping("/top-3-meses-aluno/{id}")
    fun top3MesesAluno(@PathVariable("id") id: Int): List<AgendamentoVisaoRepository>

    @GetMapping("/listagem-agendamento-aluno")
    fun buscarListaAgendamentoAluno(): List<Andamento>

    @GetMapping("/historico-agendamento-aluno")
    fun listaHistoricoAgendamentoAluno(): List<Andamento>

    @GetMapping("/agendamentos-passados/{id}")
    fun listaAulasRealizadasAluno(@PathVariable("id") id: Int): List<AgendamentoProximosProjection>

}