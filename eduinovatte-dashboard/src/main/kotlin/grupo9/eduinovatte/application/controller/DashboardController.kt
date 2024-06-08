package grupo9.eduinovatte.controller

import grupo9.eduinovatte.application.dto.response.UsuarioResponse
import grupo9.eduinovatte.domain.service.UsuarioService
import grupo9.eduinovatte.model.Usuario
import grupo9.eduinovatte.model.UsuarioNicho
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDate


@RestController
@RequestMapping("/dashboard")
class DashboardController(
    val usuarioService: UsuarioService,
    val usuarioController: UsuarioController
) {

    @Operation(summary = "Busque os alunos novos pela data inferida", description = "Busque os usuários novos do último mês.")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Retornado com sucesso"),
        ApiResponse(responseCode = "204", description = "Nenhum usuario")
    ])
    @GetMapping("/{tipo}/{dataCorte}")
    fun buscaUsuariosNovoPelaDataCorte(
        @PathVariable tipo: String,
        @PathVariable dataCorte: LocalDate
    ): ResponseEntity<List<UsuarioResponse>>{
        val tipoAcesso = usuarioController.retornaNivelAcessoNome(tipo)
        if(tipoAcesso == null) return ResponseEntity.status(403).build()
        val usuarioNicho = usuarioService.buscaUsuariosNovos(tipoAcesso, dataCorte)

        return ResponseEntity.status(200).body(usuarioNicho)
    }

    @Operation(summary = "Busque os alunos novos no ultimo vez", description = "Busque os usuários novos do último mês.")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Retornado com sucesso"),
        ApiResponse(responseCode = "204", description = "Nenhum usuario")
    ])
    @GetMapping("/{tipo}/mes")
    fun buscaUsuariosNovoNoUltimoMes(
        @PathVariable tipo: String
    ): ResponseEntity<List<UsuarioResponse>>{
        val tipoAcesso = usuarioController.retornaNivelAcessoNome(tipo)
        if(tipoAcesso == null) return ResponseEntity.status(403).build()
        val dataCorte = LocalDate.now().minusMonths(1)
        val usuarioNicho = usuarioService.buscaUsuariosNovos(tipoAcesso, dataCorte)

        return ResponseEntity.status(200).body(usuarioNicho)
    }

    @Operation(summary = "Busque os alunos novos no ultimo vez", description = "Busque os usuários novos do último mês.")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Retornado com sucesso"),
        ApiResponse(responseCode = "204", description = "Nenhum usuario")
    ])
    @GetMapping("/{tipo}/ano")
    fun buscaUsuariosNovoNoUltimoAno(
        @PathVariable tipo: String
    ): ResponseEntity<List<UsuarioResponse>>{
        val tipoAcesso = usuarioController.retornaNivelAcessoNome(tipo)
        if(tipoAcesso == null) return ResponseEntity.status(403).build()
        val dataCorte = LocalDate.now().minusYears(1)
        val usuarioNicho = usuarioService.buscaUsuariosNovos(tipoAcesso, dataCorte)

        return ResponseEntity.status(200).body(usuarioNicho)
    }

}

