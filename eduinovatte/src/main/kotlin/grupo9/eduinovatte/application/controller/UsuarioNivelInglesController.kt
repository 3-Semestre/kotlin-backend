package grupo9.eduinovatte.application.controller

import grupo9.eduinovatte.domain.model.UsuarioNivelIngles
import grupo9.eduinovatte.domain.service.UsuarioNivelInglesService
import grupo9.eduinovatte.model.UsuarioNicho
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/usuario-nivel-ingles")
class UsuarioNivelInglesController (
    val usuarioNivelInglesService: UsuarioNivelInglesService
) {
    @Operation(summary = "Salve um usuario nivel ingles", description = "Salve um nivel ingles com as informações dele.")
    @ApiResponses(value = [
        ApiResponse(responseCode = "201", description = "Criado com sucesso"),
        ApiResponse(responseCode = "500", description = "Usuario inválido")
    ])
    @PostMapping()
    fun salvaUsuarioNivelIngles(
        @RequestBody @Valid novoUsuarioNivelIngles: UsuarioNivelIngles
    ): ResponseEntity<UsuarioNivelIngles> {
        val usuarioNivelIngles = usuarioNivelInglesService.salvar(novoUsuarioNivelIngles)

        return ResponseEntity.status(201).body(usuarioNivelIngles)
    }

    @Operation(summary = "Busque os usuario com o nivel ingles", description = "Busque todos os usuarios com filtro nivel ingles dos professores e alunos.")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Usuarios encontrados encontrados"),
        ApiResponse(responseCode = "204", description = "Nenhum Usuarios encontrado")
    ])
    @GetMapping("/nivel-ingles/{id}")
    fun buscarUsuarioPeloNivelIngles(@PathVariable id: Int): ResponseEntity<List<UsuarioNivelIngles>> {
        val usuarioNivelIngles = usuarioNivelInglesService.buscaPorNivelIngles(id)

        return if (usuarioNivelIngles.isEmpty()) {
            ResponseEntity.status(204).build()
        } else {
            ResponseEntity.status(200).body(usuarioNivelIngles)
        }
    }

    @Operation(summary = "Busque os usuariosnichos", description = "Busque todos os usuarios com filtro nicho dos professores e alunos.")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Usuarios encontrados encontrados"),
        ApiResponse(responseCode = "204", description = "Nenhum Usuarios encontrado")
    ])
    @GetMapping()
    @CrossOrigin
    fun buscarNivelIngles(): ResponseEntity<List<UsuarioNivelIngles>> {
        val niveisIngles = usuarioNivelInglesService.buscaNiveisIngles()

        return if (niveisIngles.isEmpty()) {
            ResponseEntity.status(204).build()
        } else {
            ResponseEntity.status(200).body(niveisIngles)
        }
    }

    @Operation(summary = "Busque os usuario com o filtro", description = "Busque todos os usuarios com filtro nivel ingles dos professores e alunos.")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Usuarios encontrados encontrados"),
        ApiResponse(responseCode = "204", description = "Nenhum Usuarios encontrado")
    ])
    @GetMapping("/usuario/{id}")
    @CrossOrigin
    fun buscarUsuarioPeloId(@PathVariable id: Int): ResponseEntity<List<UsuarioNivelIngles>> {

        val usuarioNivelIngles = usuarioNivelInglesService.buscaPorIdUsuario(id)
        return if (usuarioNivelIngles.isEmpty()) {
            ResponseEntity.status(204).build()
        } else {
            ResponseEntity.status(200).body(usuarioNivelIngles)
        }
    }

    @Operation(summary = "Busque os usuario com o filtro", description = "Busque todos os usuarios com filtro nivel ingles dos professores e alunos.")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Usuarios encontrados encontrados"),
        ApiResponse(responseCode = "204", description = "Nenhum Usuarios encontrado")
    ])
    @DeleteMapping("/{id}")
    fun deleta(@PathVariable id: Int): ResponseEntity<List<UsuarioNivelIngles>> {
        val usuarios = usuarioNivelInglesService.deleta(id)
        return ResponseEntity.status(204).build()
    }
}