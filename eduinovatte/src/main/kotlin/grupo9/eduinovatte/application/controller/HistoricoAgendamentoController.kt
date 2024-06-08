package grupo9.eduinovatte.controller

import grupo9.eduinovatte.application.dto.response.HistoricoAgendamentoResponse
import grupo9.eduinovatte.domain.model.HistoricoAgendamento
import grupo9.eduinovatte.domain.service.HistoricoAgendamentoService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.modelmapper.ModelMapper
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/historico-agendamento")
class HistoricoAgendamentoController (
    val historicoAgendamentoService: HistoricoAgendamentoService,
    val mapper: ModelMapper = ModelMapper()
){
    fun retornaHistorico(historico: List<HistoricoAgendamento>): List<HistoricoAgendamentoResponse> {

        val dto = historico.map {
            mapper.map(it, HistoricoAgendamentoResponse::class.java)
        }

        return dto
    }


    @Operation(summary = "Busque o historico de status de todos os agendamentos", description = "Busque o historico de status de todos os agendamentos do sistema.")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Historicos de agendamentos encontrados"),
        ApiResponse(responseCode = "204", description = "Nenhum historico de agendamentos encontrado")
    ])
    @GetMapping
    fun buscaHistoricoAgendamentos(): ResponseEntity<List<HistoricoAgendamentoResponse>> {
        val listaDeHistorico = historicoAgendamentoService.buscaHistorico()
        if (listaDeHistorico.isEmpty()) return ResponseEntity.status(204).build()
        val historico = retornaHistorico(listaDeHistorico)
        return ResponseEntity.status(200).body(historico)
    }
}