package grupo9.eduinovatte.controller

import grupo9.eduinovatte.domain.model.entity.Nicho
import grupo9.eduinovatte.service.NichoRepository
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/nichos")
class NichoController (
    val nichoRepository: NichoRepository
){
    @Operation(summary = "Busque os nichos", description = "Busque todos os nichos dos professores e alunos.")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Nichos encontrados"),
        ApiResponse(responseCode = "204", description = "Nenhum nicho encontrado")
    ])
    @GetMapping
    @CrossOrigin
    fun buscaNichos(): ResponseEntity<List<Nicho>> {
        val nichos = nichoRepository.findAll()
        if(nichos.isEmpty()) return ResponseEntity.status(204).build()
        return ResponseEntity.status(200).body(nichos)
    }

}