package grupo9.eduinovatte.controller

import grupo9.eduinovatte.application.dto.response.*
import grupo9.eduinovatte.domain.model.Andamento
import grupo9.eduinovatte.domain.service.AgendamentoService
import grupo9.eduinovatte.domain.service.UsuarioService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
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

    @Operation(summary = "Busque os alunos novos no ultimo vez", description = "Busque os usuários novos do último mês.")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Retornado com sucesso"),
        ApiResponse(responseCode = "204", description = "Nenhum usuario")
    ])
    @CrossOrigin
    @GetMapping("/ultimos-3-agendamentos-professor")
    fun buscarProximos3AgendamentosProfessor(): ResponseEntity<List<AgendamentoAlunoProjection>>{
        val usuarioNicho = agendamentoService.buscarUltimos3AgendamentosProfessor()

        return ResponseEntity.status(200).body(usuarioNicho)
    }

    @CrossOrigin
    @GetMapping("/qtd-agendamento-mes-professor")
    fun qtdAgendamentoMes(): ResponseEntity<Int>{
        val usuarioNicho = agendamentoService.qtdAgendamentoMes()

        return ResponseEntity.status(200).body(usuarioNicho)
    }

    @CrossOrigin
    @GetMapping("/tempo-confirmacao")
    fun tempoConfirmacao(): ResponseEntity<Int>{
        val usuarioNicho = agendamentoService.tempoConfirmacao()

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

    @GetMapping("/taxa-cancelamento")
    fun taxaCancelamento(): ResponseEntity<Float>{
        val usuarioNicho = agendamentoService.taxaCancelamento()

        return ResponseEntity.status(200).body(usuarioNicho)
    }
    @CrossOrigin
    @GetMapping("/proximos-agendamentos-professor")
    fun proximosAgendamentos(): ResponseEntity<List<AgendamentoProximosProjection>>{
        val proximosAgendamentos = agendamentoService.proximosAgendamentos()

        return ResponseEntity.status(200).body(proximosAgendamentos)
    }

    @GetMapping("/agendamentos-passados-professor")
    fun agendamentosPassados(): ResponseEntity<List<AgendamentoProximosProjection>>{
        val proximosAgendamentos = agendamentoService.agendamentosPassados()

        return ResponseEntity.status(200).body(proximosAgendamentos)
    }

    @GetMapping("/todos-professores")
    fun todosProfessores(): ResponseEntity<List<UsuarioResponse>>{
        val proximosAgendamentos = usuarioService.listarProfessores()

        return ResponseEntity.status(200).body(proximosAgendamentos)
    }

    @GetMapping("/todos-alunos")
    fun todosAlunos(): ResponseEntity<List<UsuarioResponse>>{
        val proximosAgendamentos = usuarioService.todosAlunos()

        return ResponseEntity.status(200).body(proximosAgendamentos)
    }


    @GetMapping("/ultimos-3-agendamentos-aluno")
    fun buscarProximos3AgendamentosAluno(): ResponseEntity<List<AgendamentoAlunoProjection>>{
        val usuarioNicho = agendamentoService.buscarUltimos3AgendamentosAluno()

        return ResponseEntity.status(200).body(usuarioNicho)
    }

    @CrossOrigin
    @GetMapping("/visao-mes-aluno")
    fun visaoPorMes(): ResponseEntity<List<AgendamentoVisaoRepository>>{
        val usuarioNicho = agendamentoService.visaoPorMesAluno()

        return ResponseEntity.status(200).body(usuarioNicho)
    }

    @CrossOrigin
    @GetMapping("/top-3-meses-aluno")
    fun top3MesesAluno(): ResponseEntity<List<AgendamentoVisaoRepository>>{
        val usuarioNicho = agendamentoService.buscarTop3MesesAluno()

        return ResponseEntity.status(200).body(usuarioNicho)
    }

    @CrossOrigin
    @GetMapping("/listagem-agendamento-aluno")
    fun buscarListaAgendamentoAluno(): ResponseEntity<List<Andamento>>{
        val usuarioNicho = agendamentoService.buscarListaAgendamentoAluno()

        return ResponseEntity.status(200).body(usuarioNicho)
    }
    @CrossOrigin
    @GetMapping("/historico-agendamento-aluno")
    fun listaHistoricoAgendamentoAluno(): ResponseEntity<List<Andamento>>{
        val usuarioNicho = agendamentoService.listaHistoricoAgendamentoAluno()

        return ResponseEntity.status(200).body(usuarioNicho)
    }
}

