package grupo9.eduinovatte.controller

import grupo9.eduinovatte.application.dto.request.AgendamentoCadastro
import grupo9.eduinovatte.application.dto.request.AgendamentoCadastroRequest
import grupo9.eduinovatte.application.dto.request.FiltroAgendamentoForm
import grupo9.eduinovatte.application.dto.response.AgendamentoListagemResponse
import grupo9.eduinovatte.domain.service.AgendamentoService
import grupo9.eduinovatte.domain.model.entity.Agendamento
import grupo9.eduinovatte.domain.model.enums.StatusNome
import grupo9.eduinovatte.domain.repository.AgendamentosDetalhesListagemResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.modelmapper.ModelMapper
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.web.server.ResponseStatusException
import java.util.*

@RestController
@RequestMapping("/agendamento")
class AgendamentoController(
    val agendamentoService: AgendamentoService,
    val mapper: ModelMapper = ModelMapper()
) {
    fun retornaAgendamentos(historico: List<Agendamento?>): List<AgendamentoListagemResponse> {

        val dto = historico.map {
            mapper.map(it, AgendamentoListagemResponse::class.java)
        }

        return dto
    }

    @Operation(
        summary = "Busque agendamentos por usuario",
        description = "Busque todos os agendamentos de um determinado usuario."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Agendamentos encontrados"),
            ApiResponse(responseCode = "204", description = "Nenhum agendamento encontrado")
        ]
    )
    @CrossOrigin
    @GetMapping("/{tipo}/{id}")
    fun buscaHistoricoAgendamentoPorUsuario(
        @PathVariable tipo: Int,
        @PathVariable id: Int,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "5") size: Int,
        @RequestParam(defaultValue = "desc") sortDirection: String
    ): ResponseEntity<Page<AgendamentoListagemResponse>> {
        // Define a direção do sort (ascendente ou descendente)
        val direction = if (sortDirection.equals("asc", ignoreCase = true)) Sort.Direction.ASC else Sort.Direction.DESC

        // Configura a paginação e a ordenação
        val pageable: Pageable = PageRequest.of(page, size, direction, "id")

        val paginaDeHistorico = agendamentoService.buscaAgendamentosUsuario(tipo, id, pageable)

        return if (paginaDeHistorico.isEmpty) {
            ResponseEntity.status(HttpStatus.NO_CONTENT).build()
        } else {
            val dto = paginaDeHistorico.map {
                mapper.map(it, AgendamentoListagemResponse::class.java)
            }
            ResponseEntity.ok(dto)
        }
    }

    @GetMapping("/{tipo}/{id}/{mes}/{ano}")
    fun buscaAgendamentosPorUsuarioMes(
        @PathVariable tipo: Int,
        @PathVariable id: Int,
        @PathVariable mes: Int,
        @PathVariable ano: Int
    ): ResponseEntity<List<AgendamentoListagemResponse>> {
        val agendamentos = agendamentoService.buscaAgendamentosUsuarioMes(tipo, id, mes, ano)

        return if (agendamentos.isEmpty()) {
            ResponseEntity.status(HttpStatus.NO_CONTENT).build()
        } else {
            val dto = agendamentos.map {
                mapper.map(it, AgendamentoListagemResponse::class.java)
            }
            val agendamentosNaoCancelados = dto.filter { it.getStatus() != StatusNome.CANCELADO }
            ResponseEntity.ok(agendamentosNaoCancelados)
        }
    }

    @Operation(
        summary = "Busque o historico com base no tempo",
        description = "Busque o historico futuro ou passado de todos os agendamentos do sistema."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Historicos de agendamentos encontrados"),
            ApiResponse(responseCode = "204", description = "Nenhum historico de agendamentos encontrado")
        ]
    )
    @GetMapping("/historico/{id}")
    fun buscaAgendamentosPeloTempo(
        @PathVariable id: Int,
        @RequestParam tempo: String,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "5") size: Int,
        @RequestParam(defaultValue = "desc") sortDirection: String
    ): ResponseEntity<Page<AgendamentosDetalhesListagemResponse>> {
        val direction = if (sortDirection.equals("asc", ignoreCase = true)) Sort.Direction.ASC else Sort.Direction.DESC

        // Configura a paginação e a ordenação
        val pageable: Pageable = PageRequest.of(page, size, direction, "id")

        val listaDeHistorico = agendamentoService.buscaAgendamentosTempoUsuario(id, tempo, pageable)

        if (listaDeHistorico.isEmpty) throw ResponseStatusException(
            HttpStatus.NO_CONTENT,
            "Nenhum historico de agendamentos encontrado"
        )

        return ResponseEntity.status(200).body(listaDeHistorico)
    }

    @Operation(summary = "Salve um agendamentos", description = "Salve um novo agendamentos no sistema.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "201", description = "Criado com sucesso"),
            ApiResponse(responseCode = "401", description = "Erro no nível de acesso no corpo da requisição")
        ]
    )
    @PostMapping
    fun salvaAgendamento(@RequestBody novoAgendamento: AgendamentoCadastroRequest): ResponseEntity<AgendamentoCadastro> {
        val agendamentoSalvo = agendamentoService.salvarAgendamento(novoAgendamento)

        return ResponseEntity.status(201).body(agendamentoSalvo)
    }

    @GetMapping("/{id}")
    fun buscaAgendamentoPorId(
        @PathVariable id: Int,
    ): ResponseEntity<Optional<AgendamentoListagemResponse>> {
        val agendamento = agendamentoService.buscaAgendamentoPorId(id)

        return if (agendamento.isEmpty) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND)
        } else {
            val dto = agendamento.map {
                mapper.map(it, AgendamentoListagemResponse::class.java)
            }
            ResponseEntity.ok(dto)
        }
    }

    @PutMapping("/{id}")
    fun atualizaAssuntoAgendamentoPorId(
        @PathVariable id: Int,
        @RequestBody novoAssunto: String
    ): ResponseEntity<AgendamentoListagemResponse> {
        val agendamento = agendamentoService.atualizaAssuntoAgendamentoPorId(id, novoAssunto)

        return ResponseEntity.ok(mapper.map(agendamento, AgendamentoListagemResponse::class.java))
    }


    @CrossOrigin
    @PostMapping("/filtro/{tempo}/{tipo}/{id}")
    fun filtroAgendamento(
        @PathVariable tipo: String,
        @PathVariable tempo: String,
        @RequestBody filtro: FiltroAgendamentoForm,
        @PathVariable id: Int
    ): ResponseEntity<List<AgendamentoListagemResponse>> {
        val lista = when {
            tipo == "aluno" && tempo == "passado" -> agendamentoService.filtrarAlunoPassado(filtro, id)
            tipo == "aluno" && tempo == "futuro" -> agendamentoService.filtrarAlunoFuturo(filtro, id)
            tipo == "professor" && tempo == "passado" -> agendamentoService.filtrarProfessorPassado(filtro, id)
            tipo == "professor" && tempo == "futuro" -> agendamentoService.filtrarProfessorFuturo(filtro, id)
            else -> return ResponseEntity.status(401).build()
        }

        var listaAgendamentos = retornaAgendamentos(lista)

        return ResponseEntity.status(200).body(listaAgendamentos)
    }

}