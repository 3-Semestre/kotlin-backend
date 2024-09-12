package grupo9.eduinovatte.application.controller

import feign.FeignException
import grupo9.eduinovatte.application.dto.response.*
import grupo9.eduinovatte.domain.integration.DashboardGateway
import grupo9.eduinovatte.domain.model.entity.Andamento
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@RestController
@RequestMapping("/dashboard")
class DashboardController (
    val dashboardGateway: DashboardGateway
){
    @CrossOrigin
    @GetMapping("/{tipo}/{dataCorte}")
    fun buscaUsuariosNovoPelaDataCorte(
        @PathVariable tipo: String,
        @PathVariable dataCorte: LocalDate
    ): ResponseEntity<List<UsuarioResponse>?> {
        return try {
            val buscaUsuarios = dashboardGateway.buscaUsuarioNovoPorDataCorte(tipo, dataCorte)
            ResponseEntity.status(200).body(buscaUsuarios)
        } catch (e: FeignException) {
            ResponseEntity.status(e.status()).build()
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null)
        }
    }
    @CrossOrigin
    @GetMapping("/{tipo}/mes")
    fun buscaUsuariosNovoPeloMes(
        @PathVariable tipo: String
    ): ResponseEntity<List<UsuarioResponse>> {
        return try {
            val buscaUsuarios = dashboardGateway.buscaUsuarioNovoPorMes(tipo)
            ResponseEntity.status(200).body(buscaUsuarios)
        } catch (e: FeignException) {
            ResponseEntity.status(e.status()).build()
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null)
        }
    }
    @CrossOrigin
    @GetMapping("/{tipo}/ano")
    fun buscaUsuariosNovoPeloAno(
        @PathVariable tipo: String
    ): ResponseEntity<List<UsuarioResponse>> {
        return try {
            val buscaUsuarios = dashboardGateway.buscaUsuarioNovoPorAno(tipo)
            ResponseEntity.status(200).body(buscaUsuarios)
        } catch (e: FeignException) {
            ResponseEntity.status(e.status()).build()
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null)
        }
    }
    @CrossOrigin
    @GetMapping("/ultimos-3-agendamentos-professor/{id}")
    fun buscarProximos3AgendamentosProfessor(@PathVariable id: Int): ResponseEntity<List<AgendamentoAlunoProjection>?> {
        return try {
            val qtd = dashboardGateway.buscarProximos3AgendamentosProfessor(id)
            ResponseEntity.status(HttpStatus.OK).body(qtd)
        } catch (e: FeignException) {
                    ResponseEntity.status(e.status()).build()
                } catch (e: Exception) {
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null)
            }
        }
    @CrossOrigin
        @GetMapping("/qtd-agendamento-mes-professor/{id}")
        fun qtdAgendamentoMes(@PathVariable id: Int): ResponseEntity<Int?> {
            return try {
                val qtd = dashboardGateway.qtdAgendamentoMes(id)
                ResponseEntity.status(HttpStatus.OK).body(qtd)
            } catch (e: FeignException) {
                ResponseEntity.status(e.status()).build()
            } catch (e: Exception) {
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null)
            }
        }
    @CrossOrigin
        @GetMapping("/tempo-confirmacao")
        fun tempoConfirmacao(): ResponseEntity<Int?> {
            return try {
                val tempo = dashboardGateway.tempoConfirmacao()
                ResponseEntity.status(HttpStatus.OK).body(tempo)
            } catch (e: FeignException) {
                ResponseEntity.status(e.status()).build()
            } catch (e: Exception) {
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null)
            }
        }
    @CrossOrigin
        @GetMapping("/qtd-novos-alunos-mes")
        fun qtdNovosAlunosMes(): ResponseEntity<Int?> {
            return try {
                val qtd = dashboardGateway.qtdNovosAlunosMes()
                ResponseEntity.status(HttpStatus.OK).body(qtd)
            } catch (e: FeignException) {
                ResponseEntity.status(e.status()).build()
            } catch (e: Exception) {
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null)
            }
        }
        @CrossOrigin
        @GetMapping("/qtd-cancelamento-alunos")
        fun qtdCancelamentoMes(): ResponseEntity<Int?> {
            return try {
                val qtd = dashboardGateway.qtdCancelamentoMes()
                ResponseEntity.status(HttpStatus.OK).body(qtd)
            } catch (e: FeignException) {
                ResponseEntity.status(e.status()).build()
            } catch (e: Exception) {
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null)
            }
        }
        @CrossOrigin
        @GetMapping("/qtd-conclusao")
        fun qtdConclusaoOuNao(): ResponseEntity<List<AgendamentoConclusaoOuNaoProjection>?> {
            return try {
                val qtd = dashboardGateway.qtdConclusaoOuNao()
                ResponseEntity.status(HttpStatus.OK).body(qtd)
            } catch (e: FeignException) {
                ResponseEntity.status(e.status()).build()
            } catch (e: Exception) {
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null)
            }
        }
    @CrossOrigin
        @GetMapping("/taxa-cancelamento")
        fun taxaCancelamento(): ResponseEntity<Float?> {
            return try {
                val taxa = dashboardGateway.taxaCancelamento()
                ResponseEntity.status(HttpStatus.OK).body(taxa)
            } catch (e: FeignException) {
                ResponseEntity.status(e.status()).build()
            } catch (e: Exception) {
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null)
            }
        }
    @CrossOrigin
        @GetMapping("/proximos-agendamentos-professor/{id}")
        fun proximosAgendamentos(@PathVariable id: Int): ResponseEntity<List<AgendamentoProximosProjection>?> {
            return try {
                val agendamentos = dashboardGateway.proximosAgendamentos(id)
                ResponseEntity.status(HttpStatus.OK).body(agendamentos)
            } catch (e: FeignException) {
                ResponseEntity.status(e.status()).build()
            } catch (e: Exception) {
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null)
            }
        }

    @CrossOrigin
    @GetMapping("/proximos-agendamentos-aluno/{id}")
    fun proximosAgendamentosAluno(@PathVariable id: Int): ResponseEntity<List<AgendamentoProximosProjection>?> {
        return try {
            val agendamentos = dashboardGateway.proximosAgendamentosAluno(id)
            ResponseEntity.status(HttpStatus.OK).body(agendamentos)
        } catch (e: FeignException) {
            ResponseEntity.status(e.status()).build()
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null)
        }
    }
    @CrossOrigin
        @GetMapping("/agendamentos-passados-professor/{id}")
        fun agendamentosPassados(@PathVariable id: Int): ResponseEntity<List<AgendamentoProximosProjection>?> {
            return try {
                val agendamentos = dashboardGateway.agendamentosPassados(id)
                ResponseEntity.status(HttpStatus.OK).body(agendamentos)
            } catch (e: FeignException) {
                ResponseEntity.status(e.status()).build()
            } catch (e: Exception) {
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null)
            }
        }
    @CrossOrigin
        @GetMapping("/todos-professores")
        fun todosProfessores(): ResponseEntity<List<UsuarioResponse>?> {
            return try {
                val professores = dashboardGateway.todosProfessores()
                ResponseEntity.status(HttpStatus.OK).body(professores)
            } catch (e: FeignException) {
                ResponseEntity.status(e.status()).build()
            } catch (e: Exception) {
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null)
            }
        }
    @CrossOrigin
        @GetMapping("/todos-alunos")
        fun todosAlunos(): ResponseEntity<List<UsuarioResponse>?> {
            return try {
                val alunos = dashboardGateway.todosAlunos()
                ResponseEntity.status(HttpStatus.OK).body(alunos)
            } catch (e: FeignException) {
                ResponseEntity.status(e.status()).build()
            } catch (e: Exception) {
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null)
            }
        }
        @CrossOrigin
        @GetMapping("/ultimos-3-agendamentos-aluno/{id}")
        fun buscarProximos3AgendamentosAluno(@PathVariable id: Int): ResponseEntity<List<AgendamentoAlunoProjection>?> {
            return try {
                val agendamentos = dashboardGateway.buscarProximos3AgendamentosAluno(id)
                ResponseEntity.status(HttpStatus.OK).body(agendamentos)
            } catch (e: FeignException) {
                ResponseEntity.status(e.status()).build()
            } catch (e: Exception) {
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null)
            }
        }
    @CrossOrigin
        @GetMapping("/visao-mes-aluno/{id}")
        fun visaoPorMes(@PathVariable id: Int): ResponseEntity<List<AgendamentoVisaoRepository>?> {
            return try {
                val visao = dashboardGateway.visaoPorMes(id)
                ResponseEntity.status(HttpStatus.OK).body(visao)
            } catch (e: FeignException) {
                ResponseEntity.status(e.status()).build()
            } catch (e: Exception) {
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null)
            }
        }
        @CrossOrigin
        @GetMapping("/top-3-meses-aluno/{id}")
        fun top3MesesAluno(@PathVariable id: Int): ResponseEntity<List<AgendamentoVisaoRepository>?> {
            return try {
                val meses = dashboardGateway.top3MesesAluno(id)
                ResponseEntity.status(HttpStatus.OK).body(meses)
            } catch (e: FeignException) {
                ResponseEntity.status(e.status()).build()
            } catch (e: Exception) {
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null)
            }
        }
    @CrossOrigin
        @GetMapping("/listagem-agendamento-aluno")
        fun buscarListaAgendamentoAluno(): ResponseEntity<List<Andamento>?> {
            return try {
                val lista = dashboardGateway.buscarListaAgendamentoAluno()
                ResponseEntity.status(HttpStatus.OK).body(lista)
            } catch (e: FeignException) {
                ResponseEntity.status(e.status()).build()
            } catch (e: Exception) {
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null)
            }
        }

    @CrossOrigin
    @GetMapping("/agendamentos-passados-aluno/{id}")
    fun buscarAulasRealizadasAluno(@PathVariable id: Int): ResponseEntity<List<AgendamentoProximosProjection>?> {
        return try {
            val lista = dashboardGateway.listaAulasRealizadasAluno(id)
            ResponseEntity.status(HttpStatus.OK).body(lista)
        } catch (e: FeignException) {
            ResponseEntity.status(e.status()).build()
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null)
        }
    }

    @ExceptionHandler(FeignException::class)
    fun handleFeignException(e: FeignException): ResponseEntity<List<UsuarioResponse>?> {
        return when (e.status()) {
            400 -> ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
            404 -> ResponseEntity.status(HttpStatus.NOT_FOUND).build()
            else -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
        }
    }

    @ExceptionHandler(Exception::class)
    fun handleGenericException(e: Exception): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro inesperado: ${e.message}")
    }
}