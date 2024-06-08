package grupo9.eduinovatte.domain.integration

import grupo9.eduinovatte.application.dto.response.UsuarioResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import java.time.LocalDate

@FeignClient(name = "dashboard", url = "http://localhost:7000/dashboard")
interface DashboardGateway {

    @GetMapping("/{tipo}/{dataCorte}")
    abstract fun buscaUsuarioNovoPorDataCorte(@PathVariable tipo: String, @PathVariable dataCorte: LocalDate): List<UsuarioResponse>
    @GetMapping("/{tipo}/mes")
    abstract fun buscaUsuarioNovoPorMes(@PathVariable tipo: String): List<UsuarioResponse>
    @GetMapping("/{tipo}/ano")
    abstract fun buscaUsuarioNovoPorAno(@PathVariable tipo: String): List<UsuarioResponse>

}