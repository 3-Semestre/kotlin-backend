package grupo9.eduinovatte.application.controller

import feign.FeignException
import grupo9.eduinovatte.application.dto.response.UsuarioResponse
import grupo9.eduinovatte.controller.UsuarioController
import grupo9.eduinovatte.domain.integration.DashboardGateway
import grupo9.eduinovatte.domain.service.UsuarioService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@RestController
@RequestMapping("/dashboard")
class DashboardController (
    val dashboardGateway: DashboardGateway
){

    @GetMapping("/{tipo}/{dataCorte}")
    fun buscaUsuariosNovoPelaDataCorte(
        @PathVariable tipo: String,
        @PathVariable dataCorte: LocalDate
    ): ResponseEntity<List<UsuarioResponse>?> {
        return try {
            val buscaUsuarios = dashboardGateway.buscaUsuarioNovoPorDataCorte(tipo, dataCorte)
            ResponseEntity.status(200).body(buscaUsuarios)
        } catch (e: FeignException) {
            handleFeignException(e)
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null)
        }
    }

    @GetMapping("/{tipo}/mes")
    fun buscaUsuariosNovoPeloMes(
        @PathVariable tipo: String
    ): ResponseEntity<List<UsuarioResponse>> {
        return try {
            val buscaUsuarios = dashboardGateway.buscaUsuarioNovoPorMes(tipo)
            ResponseEntity.status(200).body(buscaUsuarios)
        } catch (e: FeignException) {
            ResponseEntity.status(e.status()).build()
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null)
        }
    }

    @GetMapping("/{tipo}/ano")
    fun buscaUsuariosNovoPeloAno(
        @PathVariable tipo: String
    ): ResponseEntity<List<UsuarioResponse>> {
        return try {
            val buscaUsuarios = dashboardGateway.buscaUsuarioNovoPorAno(tipo)
            ResponseEntity.status(200).body(buscaUsuarios)
        } catch (e: FeignException) {
            ResponseEntity.status(e.status()).build()
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null)
        }
    }

    @ExceptionHandler(FeignException::class)
    fun handleFeignException(e: FeignException): ResponseEntity<List<UsuarioResponse>?> {
        return when (e.status()) {
            400 -> ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
            404 -> ResponseEntity.status(HttpStatus.NOT_FOUND).build()
            else -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
        }
    }

    @ExceptionHandler(Exception::class)
    fun handleGenericException(e: Exception): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro inesperado: ${e.message}")
    }
}