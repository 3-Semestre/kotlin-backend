package grupo9.eduinovatte.controller

import grupo9.eduinovatte.domain.model.entity.Meta
import grupo9.eduinovatte.domain.service.MetaService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/metas")
class MetasController(
    val metaService: MetaService
) {

    @Operation(summary = "Salve uma meta", description = "Salve uma meta de um professor.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "201", description = "Criado com sucesso"),
            ApiResponse(responseCode = "403", description = "Usuario inválido"),
            ApiResponse(responseCode = "409", description = "Meta para usuario já salva")
        ]
    )
    @PostMapping()
    @CrossOrigin
    fun salvaMeta(
        @RequestBody @Valid novaMeta: Meta
    ): ResponseEntity<Meta> {
        if(novaMeta.usuario.id !== null){
            val buscaMeta = metaService.buscaPorProfessor(novaMeta.usuario.id!!)
            if(buscaMeta.isPresent && buscaMeta.get().isEmpty()){
                val meta = metaService.salvar(novaMeta)

                return ResponseEntity.status(201).body(meta)
            }
            return ResponseEntity.status(409).build()
        }
        return ResponseEntity.status(403).build()
    }

    @Operation(summary = "Edite uma meta", description = "Edite uma meta com as informações dele.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "201", description = "Criado com sucesso"),
            ApiResponse(responseCode = "500", description = "Usuario inválido")
        ]
    )
    @PutMapping()
    @CrossOrigin
    fun editaMeta(
        @RequestBody @Valid novaMeta: Meta
    ): ResponseEntity<Meta> {
        if(novaMeta.usuario.id !== null ){
            val buscaMeta = metaService.buscaPorProfessor(novaMeta.usuario.id!!)
            if(buscaMeta.isPresent && buscaMeta.get().isNotEmpty()){
                val deletado = metaService.removerPorProfessor(novaMeta.usuario.id!!)
                if (deletado > 0) {
                    val usuarioNicho = metaService.salvar(novaMeta)
                    return ResponseEntity.status(201).body(usuarioNicho)
                }
            }
        }
        return ResponseEntity.status(403).build()
    }

    @Operation(
        summary = "Busque os usuariosnichos",
        description = "Busque todos os usuarios com filtro nicho dos professores e alunos."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Usuarios encontrados encontrados"),
            ApiResponse(responseCode = "204", description = "Nenhum Usuarios encontrado")
        ]
    )
    @GetMapping()
    fun buscarMetas(): ResponseEntity<List<Meta>> {
        val metas = metaService.buscaMetas()

        return if (metas.isEmpty()) {
            ResponseEntity.status(204).build()
        } else {
            ResponseEntity.status(200).body(metas)
        }
    }

    @Operation(
        summary = "Busque os usuario com o filtro",
        description = "Busque todos os usuarios com filtro nicho dos professores e alunos."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Usuarios encontrados encontrados"),
            ApiResponse(responseCode = "204", description = "Nenhum Usuarios encontrado")
        ]
    )
    @GetMapping("/usuario/{id}")
    @CrossOrigin
    fun buscarUsuarioPeloId(@PathVariable id: Int): ResponseEntity<List<Meta>> {

        val usuarioNichos = metaService.buscaPorProfessor(id)
        return if (usuarioNichos.isEmpty()) {
            ResponseEntity.status(204).build()
        } else {
            ResponseEntity.status(200).body(usuarioNichos.get())
        }
    }

    @Operation(
        summary = "Busque os usuario com o filtro",
        description = "Busque todos os usuarios com filtro nicho dos professores e alunos."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Usuarios encontrados encontrados"),
            ApiResponse(responseCode = "204", description = "Nenhum Usuarios encontrado")
        ]
    )
    @DeleteMapping("/{id}")
    fun deleta(@PathVariable id: Int): ResponseEntity<List<Meta>> {

        val usuarios = metaService.deleta(id)
        return ResponseEntity.status(204).build()
    }

}

