package grupo9.eduinovatte.application.controller

import grupo9.eduinovatte.application.dto.request.UsuarioNivelRequest
import grupo9.eduinovatte.domain.model.entity.UsuarioNivelIngles
import grupo9.eduinovatte.domain.repository.NivelInglesRepository
import grupo9.eduinovatte.domain.service.impl.UsuarioNivelInglesServiceImpl
import grupo9.eduinovatte.domain.service.impl.UsuarioServiceImpl
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/usuario-nivel-ingles")
class UsuarioNivelInglesController(
    val usuarioNivelInglesService: UsuarioNivelInglesServiceImpl,
    val usuarioService: UsuarioServiceImpl,
    val nivelInglesRepository: NivelInglesRepository
) {
    @Operation(
        summary = "Salve um usuario nivel ingles",
        description = "Salve um nivel ingles com as informações dele."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "201", description = "Criado com sucesso"),
            ApiResponse(responseCode = "500", description = "Usuario inválido")
        ]
    )
    @PostMapping()
    fun salvaUsuarioNivelIngles(
        @RequestBody @Valid novoUsuarioNivelIngles: UsuarioNivelIngles
    ): ResponseEntity<UsuarioNivelIngles> {
        val usuarioNivelIngles = usuarioNivelInglesService.salvar(novoUsuarioNivelIngles)

        return ResponseEntity.status(201).body(usuarioNivelIngles)
    }

    @Operation(
        summary = "Busque os usuario com o nivel ingles",
        description = "Busque todos os usuarios com filtro nivel ingles dos professores e alunos."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Usuarios encontrados encontrados"),
            ApiResponse(responseCode = "204", description = "Nenhum Usuarios encontrado")
        ]
    )
    @GetMapping("/nivel-ingles/{id}")
    fun buscarUsuarioPeloNivelIngles(@PathVariable id: Int): ResponseEntity<List<UsuarioNivelIngles>> {
        val usuarioNivelIngles = usuarioNivelInglesService.buscaPorNivelIngles(id)

        return if (usuarioNivelIngles.isEmpty()) {
            ResponseEntity.status(204).build()
        } else {
            ResponseEntity.status(200).body(usuarioNivelIngles)
        }
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
    @CrossOrigin
    fun buscarNivelIngles(): ResponseEntity<List<UsuarioNivelIngles>> {
        val niveisIngles = usuarioNivelInglesService.buscaNiveisIngles()

        return if (niveisIngles.isEmpty()) {
            ResponseEntity.status(204).build()
        } else {
            ResponseEntity.status(200).body(niveisIngles)
        }
    }

    @Operation(
        summary = "Busque os usuario com o filtro",
        description = "Busque todos os usuarios com filtro nivel ingles dos professores e alunos."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Usuarios encontrados encontrados"),
            ApiResponse(responseCode = "204", description = "Nenhum Usuarios encontrado")
        ]
    )
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

    @Operation(
        summary = "Busque os usuario com o filtro",
        description = "Busque todos os usuarios com filtro nivel ingles dos professores e alunos."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Usuarios encontrados encontrados"),
            ApiResponse(responseCode = "204", description = "Nenhum Usuarios encontrado")
        ]
    )
    @DeleteMapping("/{id}")
    fun deleta(@PathVariable id: Int): ResponseEntity<List<UsuarioNivelIngles>> {
        val usuarios = usuarioNivelInglesService.deleta(id)
        return ResponseEntity.status(204).build()
    }

    @Operation(summary = "Edite um usuario nicho", description = "Salve um usuario nicho com as informações dele.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "201", description = "Criado com sucesso"),
            ApiResponse(responseCode = "500", description = "Usuario inválido")
        ]
    )
    @PutMapping("/{id}")
    @CrossOrigin
    fun editaUsuarioNivelIngles(
        @PathVariable id: Int,
        @RequestBody idNivelIngles: Int
    ): ResponseEntity<UsuarioNivelIngles> {
        val novoNivel = usuarioNivelInglesService.atualizarNivelUsuario(id, idNivelIngles)
        return ResponseEntity.status(201).body(novoNivel)
    }

    @PostMapping("/professor/{id}")
    @CrossOrigin
    fun editaUsuarioNivelProfessor(
        @PathVariable id: Int,
        @RequestBody @Valid novoUsuarioNivel: UsuarioNivelRequest
    ): ResponseEntity<List<UsuarioNivelIngles>> {
        val niveisAtuais = usuarioNivelInglesService.buscaPorIdUsuario(id)

        val niveisParaRemover = niveisAtuais.filter { nivelAtual ->
            novoUsuarioNivel.nivelNaoSelecionados.contains(nivelAtual.nivelIngles.id)
        }

        val niveisParaAdicionar = novoUsuarioNivel.nivelSelecionados.filter { novoNivelId ->
            niveisAtuais.none { nivelAtual -> nivelAtual.nivelIngles.id == novoNivelId }
        }

        niveisParaRemover.forEach { nivel ->
            usuarioNivelInglesService.buscarNivelPorUsuarioNivel(id, nivel.nivelIngles.id!!).ifPresent { usuarioNivel ->
                usuarioNivelInglesService.deleta(usuarioNivel.id!!)
            }
        }

        val novosUsuarioNiveis = niveisParaAdicionar.map { novoNivelId ->
            UsuarioNivelIngles(
                usuario = usuarioService.usuarioRepository.findById(id).orElseThrow {
                    RuntimeException("Usuário não encontrado")
                },
                nivelIngles = nivelInglesRepository.findById(novoNivelId).orElseThrow {
                    RuntimeException("Nível de inglês não encontrado")
                }
            )
        }.map { usuarioNivelInglesService.salvar(it) }

        return ResponseEntity.ok(novosUsuarioNiveis)
    }


}