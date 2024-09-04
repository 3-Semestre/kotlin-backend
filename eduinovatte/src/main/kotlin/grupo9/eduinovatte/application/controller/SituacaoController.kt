package grupo9.eduinovatte.controller

import grupo9.eduinovatte.domain.model.entity.Situacao
import grupo9.eduinovatte.domain.service.SituacaoService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/situacao")
class SituacaoController (
    val situacaoService: SituacaoService
){
    @Operation(summary = "Busque as situações", description = "Busque todos as possíveis situações dos professores e alunos.")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Situações encontrados"),
        ApiResponse(responseCode = "204", description = "Nenhuma situação encontrado")
    ])
    @GetMapping
    fun buscaSituacao(): ResponseEntity<List<Situacao>> {
        val situacoes = situacaoService.buscaSituacoes()
        if(situacoes.isEmpty()) return ResponseEntity.status(204).build()
        return ResponseEntity.status(200).body(situacoes)
    }


}