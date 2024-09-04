package grupo9.eduinovatte.controller

import grupo9.eduinovatte.domain.service.NivelAcessoService
import grupo9.eduinovatte.domain.model.entity.NivelAcesso
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/niveis-acessos")
class NivelAcessoController (
    val nivelAcessoService: NivelAcessoService
){
    @Operation(summary = "Busque os níveis de acesso", description = "Busque todos os níveis de acesso do sistema.")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Níveis de acesso encontrados"),
        ApiResponse(responseCode = "204", description = "Nenhum nível de acesso encontrado")
    ])
    @GetMapping
    fun buscaNiveisAcesso(): ResponseEntity<List<NivelAcesso>> {
        val niveis = nivelAcessoService.buscaNiveisAcesso()
        if(niveis.isEmpty()) return ResponseEntity.status(204).build()
        return ResponseEntity.status(200).body(niveis)
    }
}