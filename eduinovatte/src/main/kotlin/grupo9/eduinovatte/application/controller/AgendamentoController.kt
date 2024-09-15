package grupo9.eduinovatte.controller

import grupo9.eduinovatte.application.dto.request.AgendamentoCadastro
import grupo9.eduinovatte.application.dto.response.AgendamentoListagemResponse
import grupo9.eduinovatte.domain.service.AgendamentoService
import grupo9.eduinovatte.domain.model.entity.Agendamento
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

    @Operation(summary = "Busque todos os agendamentos", description = "Busque todos os agendamentos de um aluno.")
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
        @RequestParam(defaultValue = "10") size: Int,
        @RequestParam(defaultValue = "desc") sortDirection: String
    ): ResponseEntity<Page<AgendamentoListagemResponse>> {
        // Define a direção do sort (ascendente ou descendente)
        val direction = if (sortDirection.equals("asc", ignoreCase = true)) Sort.Direction.ASC else Sort.Direction.DESC

        // Configura a paginação e a ordenação
        val pageable: Pageable = PageRequest.of(page, size, direction, "data")

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

    @Operation(summary = "Salve um agendamentos", description = "Salve um novo agendamentos no sistema.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "201", description = "Criado com sucesso"),
            ApiResponse(responseCode = "401", description = "Erro no nível de acesso no corpo da requisição")
        ]
    )
    @PostMapping
    fun salvaAgendamento(@RequestBody novoAgendamento: Agendamento): ResponseEntity<AgendamentoCadastro> {
        val agendamentoSalvo = agendamentoService.salvarAgendamento(novoAgendamento)

        return ResponseEntity.status(201).body(agendamentoSalvo)
    }

}