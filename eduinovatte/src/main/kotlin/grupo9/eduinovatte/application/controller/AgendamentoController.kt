package grupo9.eduinovatte.controller

import grupo9.eduinovatte.application.dto.request.AgendamentoCadastro
import grupo9.eduinovatte.application.dto.response.AgendamentoListagemResponse
import grupo9.eduinovatte.application.dto.response.HistoricoAgendamentoResponse
import grupo9.eduinovatte.domain.model.HistoricoAgendamento
import grupo9.eduinovatte.domain.service.AgendamentoService
import grupo9.eduinovatte.model.Agendamento
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.modelmapper.ModelMapper
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/agendamento")
class AgendamentoController (
    val AgendamentoService: AgendamentoService,
    val mapper: ModelMapper = ModelMapper()
){
    fun retornaAgendamentos(historico: List<Agendamento>): List<AgendamentoListagemResponse> {

        val dto = historico.map {
            mapper.map(it, AgendamentoListagemResponse::class.java)
        }

        return dto
    }
    @Operation(summary = "Busque todos os agendamentos", description = "Busque todos os agendamentos do sistema.")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Agendamentos encontrados"),
        ApiResponse(responseCode = "204", description = "Nenhum agendamento encontrado")
    ])
    @GetMapping
    fun buscaHistoricoAgendamentos(): ResponseEntity<List<AgendamentoListagemResponse>> {
        val listaDeHistorico = AgendamentoService.buscaAgendamentos()
        if(listaDeHistorico.isEmpty()) return ResponseEntity.status(204).build()

        val agendamentos = retornaAgendamentos(listaDeHistorico)

        return ResponseEntity.status(200).body(agendamentos)
    }

    @Operation(summary = "Salve um agendamentos", description = "Salve um novo agendamentos no sistema.")
    @ApiResponses(value = [
        ApiResponse(responseCode = "201", description = "Criado com sucesso"),
        ApiResponse(responseCode = "401", description = "Erro no nível de acesso no corpo da requisição")
    ])
    @PostMapping
    fun salvaAgendamento(@RequestBody novoAgendamento: Agendamento): ResponseEntity<AgendamentoCadastro> {
        val agendamentoSalvo = AgendamentoService.salvarAgendamento(novoAgendamento)

        return ResponseEntity.status(201).body(agendamentoSalvo)
    }


}