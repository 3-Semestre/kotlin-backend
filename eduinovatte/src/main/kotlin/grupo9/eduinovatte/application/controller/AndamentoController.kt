package grupo9.eduinovatte.controller

import grupo9.eduinovatte.application.dto.request.AlterarStatus
import grupo9.eduinovatte.application.dto.response.AndamentoHistoricoResponse
import grupo9.eduinovatte.domain.model.entity.Andamento
import grupo9.eduinovatte.domain.service.impl.AndamentoServiceImpl
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.modelmapper.ModelMapper
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/historico-agendamento")
class AndamentoController(
    val andamentoService: AndamentoServiceImpl,
    val mapper: ModelMapper = ModelMapper()
) {
    fun retornaHistorico(historico: List<Andamento>): List<AndamentoHistoricoResponse> {

        val dto = historico.map {
            mapper.map(it, AndamentoHistoricoResponse::class.java)
        }

        return dto
    }

    @Operation(
        summary = "Busque o historico de status de todos os agendamentos",
        description = "Busque o historico de status de todos os agendamentos do sistema."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Historicos de agendamentos encontrados"),
            ApiResponse(responseCode = "204", description = "Nenhum historico de agendamentos encontrado")
        ]
    )
    @GetMapping
    fun buscaHistoricoAgendamentos(): ResponseEntity<List<AndamentoHistoricoResponse>> {
        val listaDeHistorico = andamentoService.buscaHistorico()
        if (listaDeHistorico.isEmpty()) return ResponseEntity.status(204).build()

        val historico = retornaHistorico(listaDeHistorico)
        return ResponseEntity.status(200).body(historico)
    }

    @Operation(summary = "Salve um historico", description = "Salve um novo historico no sistema.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "201", description = "Criado com sucesso"),
            ApiResponse(responseCode = "401", description = "Erro no nível de acesso no corpo da requisição")
        ]
    )
    @PostMapping
    fun atualizarHistoricoAgendamento(@RequestBody novoHistorico: AlterarStatus): ResponseEntity<Andamento> {
        val agendamentoSalvo = andamentoService.mudarHistorico(novoHistorico)

        return ResponseEntity.status(201).body(agendamentoSalvo)
    }

    @Operation(
        summary = "Busque o historico de status agendamento",
        description = "Busque o historico de status de um agendamento pelo id."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Historico encontrados"),
            ApiResponse(responseCode = "404", description = "Nenhum historico de agendamentos encontrado")
        ]
    )
    @GetMapping("/{id}")
    fun buscaHistoricoPorIdAgendamento(@PathVariable id: Int): ResponseEntity<List<AndamentoHistoricoResponse>> {
        val listaDeHistorico = andamentoService.buscaHistoricoPorIdAgendamento(id)
        if (listaDeHistorico.isEmpty()) return ResponseEntity.status(204).build()

        val historico = retornaHistorico(listaDeHistorico)
        return ResponseEntity.status(200).body(historico)
    }
}