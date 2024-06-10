package grupo9.eduinovatte.controller

import grupo9.eduinovatte.domain.repository.NivelInglesRepository
import grupo9.eduinovatte.model.Nicho
import grupo9.eduinovatte.model.NivelIngles
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/nivel-ingles")
class NivelInglesController (
    val nivelInglesRepository: NivelInglesRepository
) {
    @Operation(summary = "Busque os Niveis Ingles", description = "Busque todos os Niveis Ingles dos professores e alunos.")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Niveis Ingles encontrados"),
        ApiResponse(responseCode = "204", description = "Nenhum Niveis Ingles encontrado")
    ])
    @GetMapping
    @CrossOrigin
    fun buscaNiveis(): ResponseEntity<List<NivelIngles>> {
        val NivelIngles = nivelInglesRepository.findAll()
        if(NivelIngles.isEmpty()) return ResponseEntity.status(204).build()
        return ResponseEntity.status(200).body(NivelIngles)
    }
}