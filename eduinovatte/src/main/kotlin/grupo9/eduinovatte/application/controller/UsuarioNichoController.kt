package grupo9.eduinovatte.controller

import grupo9.eduinovatte.domain.model.entity.UsuarioNicho
import grupo9.eduinovatte.domain.service.impl.UsuarioNichoServiceImpl
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/usuario-nicho")
class UsuarioNichoController(
    val usuarioNichoService: UsuarioNichoServiceImpl
) {

    @Operation(summary = "Salve um usuario nicho", description = "Salve um usuario nicho com as informações dele.")
    @ApiResponses(value = [
        ApiResponse(responseCode = "201", description = "Criado com sucesso"),
        ApiResponse(responseCode = "500", description = "Usuario inválido")
    ])
    @PostMapping()
    @CrossOrigin
    fun salvaUsuarioNicho(
        @RequestBody @Valid novoUsuarioNicho: UsuarioNicho
    ): ResponseEntity<UsuarioNicho>{
        val usuarioNicho = usuarioNichoService.salvar(novoUsuarioNicho)

        return ResponseEntity.status(201).body(usuarioNicho)
    }

    @Operation(summary = "Salve um usuario nicho", description = "Salve um usuario nicho com as informações dele.")
    @ApiResponses(value = [
        ApiResponse(responseCode = "201", description = "Criado com sucesso"),
        ApiResponse(responseCode = "500", description = "Usuario inválido")
    ])
    @PutMapping("/{id}")
    @CrossOrigin
    fun editaUsuarioNicho(
        @PathVariable id: Int,
        @RequestBody @Valid novoUsuarioNicho: UsuarioNicho
    ): ResponseEntity<UsuarioNicho>{
        val deletado = usuarioNichoService.removerPorUsuario(id)
        if(deletado > 0){
            val usuarioNicho = usuarioNichoService.salvar(novoUsuarioNicho)
            return ResponseEntity.status(201).body(usuarioNicho)
        }
        return ResponseEntity.status(403).build()
    }


    @Operation(summary = "Busque os usuariosnichos", description = "Busque todos os usuarios com filtro nicho dos professores e alunos.")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Usuarios encontrados encontrados"),
        ApiResponse(responseCode = "204", description = "Nenhum Usuarios encontrado")
    ])
    @GetMapping()
    fun buscarUsuarioNicho(): ResponseEntity<List<UsuarioNicho>> {
        val usuarioNichos = usuarioNichoService.buscaUsuariosNichos()

        return if (usuarioNichos.isEmpty()) {
            ResponseEntity.status(204).build()
        } else {
            ResponseEntity.status(200).body(usuarioNichos)
        }
    }

    @Operation(summary = "Busque os usuario com o filtro", description = "Busque todos os usuarios com filtro nicho dos professores e alunos.")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Usuarios encontrados encontrados"),
        ApiResponse(responseCode = "204", description = "Nenhum Usuarios encontrado")
    ])
    @GetMapping("/nicho/{id}")
    fun buscarUsuarioPeloNicho(@PathVariable id: Int): ResponseEntity<List<UsuarioNicho>> {
        val usuarioNichos = usuarioNichoService.buscaPorNicho(id)

        return if (usuarioNichos.isEmpty()) {
            ResponseEntity.status(204).build()
        } else {
            ResponseEntity.status(200).body(usuarioNichos)
        }
    }

    @Operation(summary = "Busque os usuario com o filtro", description = "Busque todos os usuarios com filtro nicho dos professores e alunos.")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Usuarios encontrados encontrados"),
        ApiResponse(responseCode = "204", description = "Nenhum Usuarios encontrado")
    ])
    @GetMapping("/usuario/{id}")
    @CrossOrigin
    fun buscarUsuarioPeloId(@PathVariable id: Int): ResponseEntity<List<UsuarioNicho>> {

        val usuarioNichos = usuarioNichoService.buscaPorIdUsuario(id)
        return if (usuarioNichos.isEmpty()) {
            ResponseEntity.status(204).build()
        } else {
            ResponseEntity.status(200).body(usuarioNichos)
        }
    }

    @Operation(summary = "Busque os usuario com o filtro", description = "Busque todos os usuarios com filtro nicho dos professores e alunos.")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Usuarios encontrados encontrados"),
        ApiResponse(responseCode = "204", description = "Nenhum Usuarios encontrado")
    ])
    @DeleteMapping("/{id}")
    fun deleta(@PathVariable id: Int): ResponseEntity<List<UsuarioNicho>> {

        val usuarios = usuarioNichoService.deleta(id)
        return ResponseEntity.status(204).build()
    }

}

