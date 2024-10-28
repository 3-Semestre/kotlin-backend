package grupo9.eduinovatte.controller

import grupo9.eduinovatte.application.dto.response.*
import grupo9.eduinovatte.domain.model.Andamento
import grupo9.eduinovatte.domain.repository.AgendamentoCancelamentoPorMesProjection
import grupo9.eduinovatte.domain.repository.AgendamentoConclusaoMesAtualProjection
import grupo9.eduinovatte.domain.repository.AgendamentoConclusaoPorMesProjection
import grupo9.eduinovatte.domain.service.AgendamentoService
import grupo9.eduinovatte.domain.service.UsuarioService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDate


@RestController
@RequestMapping("/dashboard")
class DashboardController(
    val usuarioService: UsuarioService,
    val usuarioController: UsuarioController,
    val agendamentoService: AgendamentoService
) {
    @CrossOrigin
    @GetMapping("/{tipo}/{dataCorte}")
    fun buscaUsuariosNovoPelaDataCorte(
        @PathVariable tipo: String,
        @PathVariable dataCorte: LocalDate
    ): ResponseEntity<List<UsuarioResponse>>{
        val tipoAcesso = usuarioController.retornaNivelAcessoNome(tipo)
        if(tipoAcesso == null) return ResponseEntity.status(403).build()
        val usuarioNicho = usuarioService.buscaUsuariosNovos(tipoAcesso, dataCorte)

        return ResponseEntity.status(200).body(usuarioNicho)
    }
    @CrossOrigin
    @GetMapping("/{tipo}/mes")
    fun buscaUsuariosNovoNoUltimoMes(
        @PathVariable tipo: String
    ): ResponseEntity<List<UsuarioResponse>>{
        val tipoAcesso = usuarioController.retornaNivelAcessoNome(tipo)
        if(tipoAcesso == null) return ResponseEntity.status(403).build()
        val dataCorte = LocalDate.now().minusMonths(1)
        val usuarioNicho = usuarioService.buscaUsuariosNovos(tipoAcesso, dataCorte)

        return ResponseEntity.status(200).body(usuarioNicho)
    }
    @CrossOrigin
    @GetMapping("/{tipo}/ano")
    fun buscaUsuariosNovoNoUltimoAno(
        @PathVariable tipo: String
    ): ResponseEntity<List<UsuarioResponse>>{
        val tipoAcesso = usuarioController.retornaNivelAcessoNome(tipo)
        if(tipoAcesso == null) return ResponseEntity.status(403).build()
        val dataCorte = LocalDate.now().minusYears(1)
        val usuarioNicho = usuarioService.buscaUsuariosNovos(tipoAcesso, dataCorte)

        return ResponseEntity.status(200).body(usuarioNicho)
    }

    @CrossOrigin
    @GetMapping("/qtd-agendamento-mes-professor/{id}")
    fun qtdAgendamentoMes(@PathVariable id: Int): ResponseEntity<Int>{
        val usuarioNicho = agendamentoService.qtdAgendamentoMes(id)

        return ResponseEntity.status(200).body(usuarioNicho)
    }

    @CrossOrigin
    @GetMapping("/tempo-confirmacao/{id}")
    fun tempoConfirmacao(@PathVariable id: Int): ResponseEntity<Int>{
        val usuarioNicho = agendamentoService.tempoConfirmacao(id)

        return ResponseEntity.status(200).body(usuarioNicho)
    }

    @CrossOrigin
    @GetMapping("/qtd-novos-alunos-mes")
    fun qtdNovosAlunosMes(): ResponseEntity<Int>{
        val usuarioNicho = agendamentoService.qtdNovosAlunosMes()

        return ResponseEntity.status(200).body(usuarioNicho)
    }
    @CrossOrigin
    @GetMapping("/qtd-cancelamento-alunos")
    fun qtdCancelamentoMes(): ResponseEntity<Int>{
        val usuarioNicho = agendamentoService.qtdCancelamentoMes()

        return ResponseEntity.status(200).body(usuarioNicho)
    }

    @GetMapping("/qtd-conclusao")
    @CrossOrigin
    fun qtdConclusaoOuNao(): ResponseEntity<List<AgendamentoConclusaoOuNaoProjection>>{
        val usuarioNicho = agendamentoService.qtdConclusaoOuNao()

        return ResponseEntity.status(200).body(usuarioNicho)
    }
    @CrossOrigin
    @GetMapping("/taxa-cancelamento")
    fun taxaCancelamento(): ResponseEntity<Float>{
        val usuarioNicho = agendamentoService.taxaCancelamento()

        return ResponseEntity.status(200).body(usuarioNicho)
    }
    @CrossOrigin
    @GetMapping("/proximos-agendamentos-professor/{id}")
    fun proximosAgendamentos(@PathVariable id: Int): ResponseEntity<List<AgendamentoProximosProjection>>{
        val proximosAgendamentos = agendamentoService.proximosAgendamentos(id)

        return ResponseEntity.status(200).body(proximosAgendamentos)
    }

    @CrossOrigin
    @GetMapping("/proximos-agendamentos-aluno/{id}")
    fun proximosAgendamentosAluno(@PathVariable id: Int): ResponseEntity<List<AgendamentoProximosProjection>>{
        val proximosAgendamentos = agendamentoService.proximosAgendamentosAluno(id)

        return ResponseEntity.status(200).body(proximosAgendamentos)
    }
    @CrossOrigin
    @GetMapping("/agendamentos-passados-professor/{id}")
    fun agendamentosPassados(@PathVariable id: Int): ResponseEntity<List<AgendamentoProximosProjection>>{
        val proximosAgendamentos = agendamentoService.agendamentosPassados(id)

        return ResponseEntity.status(200).body(proximosAgendamentos)
    }

    @CrossOrigin
    @GetMapping("/agendamentos-passados-aluno/{id}")
    fun agendamentosPassadosAluno(@PathVariable id: Int): ResponseEntity<List<AgendamentoProximosProjection>>{
        val proximosAgendamentos = agendamentoService.agendamentosPassadosAluno(id)

        return ResponseEntity.status(200).body(proximosAgendamentos)
    }

    @CrossOrigin
    @GetMapping("/todos-professores")
    fun todosProfessores(): ResponseEntity<List<UsuarioResponse>>{
        val proximosAgendamentos = usuarioService.listarProfessores()

        return ResponseEntity.status(200).body(proximosAgendamentos)
    }

    @CrossOrigin
    @GetMapping("/todos-alunos")
    fun todosAlunos(): ResponseEntity<List<UsuarioResponse>>{
        val proximosAgendamentos = usuarioService.todosAlunos()

        return ResponseEntity.status(200).body(proximosAgendamentos)
    }

    @CrossOrigin
    @GetMapping("/ultimos-3-agendamentos-aluno/{id}")
    fun buscarProximos3AgendamentosAluno(@PathVariable id: Int): ResponseEntity<List<AgendamentoAlunoProjection>>{
        val usuarioNicho = agendamentoService.buscarUltimos3AgendamentosAluno(id)

        return ResponseEntity.status(200).body(usuarioNicho)
    }

    @CrossOrigin
    @GetMapping("/ultimos-3-agendamentos-professor/{id}")
    fun buscarProximos3AgendamentosProfessor(@PathVariable id: Int): ResponseEntity<List<AgendamentoAlunoProjection>>{
        val usuarioNicho = agendamentoService.buscarUltimos3AgendamentosProfessor(id)

        return ResponseEntity.status(200).body(usuarioNicho)
    }

    @CrossOrigin
    @GetMapping("/visao-mes-aluno/{id}")
    fun visaoPorMes(@PathVariable id: Int): ResponseEntity<List<AgendamentoVisaoRepository>>{
        val usuarioNicho = agendamentoService.visaoPorMesAluno(id)

        return ResponseEntity.status(200).body(usuarioNicho)
    }

    @CrossOrigin
    @GetMapping("/top-3-meses-aluno/{id}")
    fun top3MesesAluno(@PathVariable id: Int): ResponseEntity<List<AgendamentoVisaoRepository>>{
        val usuarioNicho = agendamentoService.buscarTop3MesesAluno(id)

        return ResponseEntity.status(200).body(usuarioNicho)
    }

    @CrossOrigin
    @GetMapping("/listagem-agendamento-aluno")
    fun buscarListaAgendamentoAluno(): ResponseEntity<Int>{
        val usuarioNicho = agendamentoService.buscarListaAgendamentoAluno()

        return ResponseEntity.status(200).body(usuarioNicho)
    }
    @CrossOrigin
    @GetMapping("/historico-agendamento-aluno")
    fun listaHistoricoAgendamentoAluno(): ResponseEntity<List<Andamento>>{
        val usuarioNicho = agendamentoService.listaHistoricoAgendamentoAluno()

        return ResponseEntity.status(200).body(usuarioNicho)
    }

    @CrossOrigin
    @GetMapping("/taxa-cancelamento-mes/{id}")
    fun taxaCancelamentoPorMes(@PathVariable id: Int): ResponseEntity<List<AgendamentoCancelamentoPorMesProjection>>{
        val usuarioNicho = agendamentoService.taxaCancelamentoPorMes(id)

        return ResponseEntity.status(200).body(usuarioNicho)
    }

    @CrossOrigin
    @GetMapping("/qtd-agendamentos-cancelados/{id}")
    fun buscaQtdAgendamentosCancelados(@PathVariable id: Int): ResponseEntity<Int>{
        val usuarioNicho = agendamentoService.buscaQtdAgendamentosCancelados(id)

        return ResponseEntity.status(200).body(usuarioNicho)
    }

    @CrossOrigin
    @GetMapping("/aulas-tranferidas-professor/{id}")
    fun buscaAulasTransferidasPorProfessor(@PathVariable id: Int): ResponseEntity<Int>{
        val usuarioNicho = agendamentoService.buscaAulasTransferidasPorProfessor(id)

        return ResponseEntity.status(200).body(usuarioNicho)
    }

    @CrossOrigin
    @GetMapping("/taxa-cumprimento-metas/{id}")
    fun buscaTaxaCumprimentoMetas(@PathVariable id: Int): ResponseEntity<List<TaxaCumprimentoRepository>>{
        val usuarioNicho = agendamentoService.buscaTaxaCumprimentoMetas(id)

        return ResponseEntity.status(200).body(usuarioNicho)
    }

    @CrossOrigin
    @GetMapping("/qtd-aluno-mes-professor/{id}")
    fun buscaQtdAlunoPorMes(@PathVariable id: Int): ResponseEntity<List<AlunosQuantidadeRepository>>{
        val usuarioNicho = agendamentoService.buscaQtdAlunoPorMes(id)

        return ResponseEntity.status(200).body(usuarioNicho)
    }

    @CrossOrigin
    @GetMapping("/aulas-concluidas-professor/{id}")
    fun buscaQtdConclusaoProfessor(@PathVariable id: Int): ResponseEntity<AgendamentoConclusaoMesAtualProjection> {
        val quantidadeConcluido = agendamentoService.buscaQtdConclusaoProfessor(id)

        return ResponseEntity.status(200).body(quantidadeConcluido)
    }

    @CrossOrigin
    @GetMapping("/aulas-concluidas-todos-meses/{id}")
    fun buscaQtdConclusaoProfessorTodosMeses(@PathVariable("id") id: Int): ResponseEntity<List<AgendamentoConclusaoPorMesProjection>> {
        val quantidadeConcluido = agendamentoService.buscaQtdConclusaoProfessorTodosMeses(id)

        return ResponseEntity.status(200).body(quantidadeConcluido)
    }
}

