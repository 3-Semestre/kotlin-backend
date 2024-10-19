package grupo9.eduinovatte.controller

import grupo9.eduinovatte.application.dto.request.UsuarioNichoRequest
import grupo9.eduinovatte.domain.model.entity.UsuarioNicho
import grupo9.eduinovatte.domain.service.impl.UsuarioNichoServiceImpl
import grupo9.eduinovatte.domain.service.impl.UsuarioServiceImpl
import grupo9.eduinovatte.service.NichoRepository
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/usuario-nicho")
class UsuarioNichoController(
    val usuarioNichoService: UsuarioNichoServiceImpl,
    val usuarioService: UsuarioServiceImpl,
    val nichoRepository: NichoRepository
) {

    @Operation(summary = "Salve um usuario nicho", description = "Salve um usuario nicho com as informações dele.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "201", description = "Criado com sucesso"),
            ApiResponse(responseCode = "500", description = "Usuario inválido")
        ]
    )
    @PostMapping()
    @CrossOrigin
    fun salvaUsuarioNicho(
        @RequestBody @Valid novoUsuarioNicho: UsuarioNicho
    ): ResponseEntity<UsuarioNicho> {
        val usuarioNicho = usuarioNichoService.salvar(novoUsuarioNicho)

        return ResponseEntity.status(201).body(usuarioNicho)
    }

    @Operation(summary = "Salve um usuario nicho", description = "Salve um usuario nicho com as informações dele.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "201", description = "Criado com sucesso"),
            ApiResponse(responseCode = "500", description = "Usuario inválido")
        ]
    )
    @PutMapping("/{id}")
    @CrossOrigin
    fun editaUsuarioNicho(
        @PathVariable id: Int,
        @RequestBody @Valid novoUsuarioNicho: UsuarioNicho
    ): ResponseEntity<UsuarioNicho> {
        val deletado = usuarioNichoService.removerPorUsuario(id)
        if (deletado > 0) {
            val usuarioNicho = usuarioNichoService.salvar(novoUsuarioNicho)
            return ResponseEntity.status(201).body(usuarioNicho)
        }
        return ResponseEntity.status(403).build()
    }

    @PostMapping("/professor/{id}")
    @CrossOrigin
    fun editaUsuarioNichoProfessor(
        @PathVariable id: Int,
        @RequestBody @Valid novoUsuarioNicho: UsuarioNichoRequest
    ): ResponseEntity<List<UsuarioNicho>> {
        val nichosAtuais = usuarioNichoService.buscaPorIdUsuario(id)

        val nichosParaRemover = nichosAtuais.filter { nichoAtual ->
            novoUsuarioNicho.nichoNaoSelecionados.contains(nichoAtual.nicho.id)
        }

        val nichosParaAdicionar = novoUsuarioNicho.nichoSelecionados.filter { novoNichoId ->
            nichosAtuais.none { nichoAtual -> nichoAtual.nicho.id == novoNichoId }
        }

        nichosParaRemover.forEach { nicho ->
            usuarioNichoService.buscarNichoPorUsuarioNicho(id, nicho.nicho.id!!).ifPresent { usuarioNicho ->
                usuarioNichoService.deleta(usuarioNicho.id!!)
            }
        }

        val novosUsuarioNichos = nichosParaAdicionar.map { novoNichoId ->
            UsuarioNicho(
                usuario = usuarioService.usuarioRepository.findById(id).orElseThrow(){
                    RuntimeException("Usuário não encontrado")
                },
                nicho = nichoRepository.findById(novoNichoId).orElseThrow(){
                    RuntimeException("Nicho não encontrado")
                }
            )
        }.map { usuarioNichoService.salvar(it) }

        return ResponseEntity.ok(novosUsuarioNichos)
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
    fun buscarUsuarioNicho(): ResponseEntity<List<UsuarioNicho>> {
        val usuarioNichos = usuarioNichoService.buscaUsuariosNichos()

        return if (usuarioNichos.isEmpty()) {
            ResponseEntity.status(204).build()
        } else {
            ResponseEntity.status(200).body(usuarioNichos)
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
    @GetMapping("/nicho/{id}")
    fun buscarUsuarioPeloNicho(@PathVariable id: Int): ResponseEntity<List<UsuarioNicho>> {
        val usuarioNichos = usuarioNichoService.buscaPorNicho(id)

        return if (usuarioNichos.isEmpty()) {
            ResponseEntity.status(204).build()
        } else {
            ResponseEntity.status(200).body(usuarioNichos)
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
    fun buscarUsuarioPeloId(@PathVariable id: Int): ResponseEntity<List<UsuarioNicho>> {

        val usuarioNichos = usuarioNichoService.buscaPorIdUsuario(id)
        return if (usuarioNichos.isEmpty()) {
            ResponseEntity.status(204).build()
        } else {
            ResponseEntity.status(200).body(usuarioNichos)
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
    fun deleta(@PathVariable id: Int): ResponseEntity<List<UsuarioNicho>> {

        val usuarios = usuarioNichoService.deleta(id)
        return ResponseEntity.status(204).build()
    }

}

