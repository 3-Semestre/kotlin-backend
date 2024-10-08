package grupo9.eduinovatte.controller

import grupo9.eduinovatte.domain.model.entity.Status
import grupo9.eduinovatte.domain.service.StatusService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/status")
class StatusController (
    val statusService: StatusService
){
    @Operation(summary = "Busque os mensagens de status dos agendamentos", description = "Busque todos as mensagens de status de agendamentos do sistema.")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Status de agendamentos encontrados"),
        ApiResponse(responseCode = "204", description = "Nenhum status de agendamentos encontrado")
    ])
    @GetMapping
    fun buscaStatusAgendamentos(): ResponseEntity<List<Status>> {
        val listaDeStatus = statusService.buscaStatus()
        if(listaDeStatus.isEmpty()) return ResponseEntity.status(204).build()
        return ResponseEntity.status(200).body(listaDeStatus)
    }

    @GetMapping("/{id}")
    fun buscaStatusPorId(@PathVariable id: Int): ResponseEntity<Status> {
        val status = statusService.buscaStatusPorId(id).orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "Status não encontrado") }
        return ResponseEntity.status(200).body(status)
    }
}