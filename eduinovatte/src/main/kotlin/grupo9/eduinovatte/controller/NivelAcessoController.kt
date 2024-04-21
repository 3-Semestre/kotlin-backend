package grupo9.eduinovatte.controller

import grupo9.eduinovatte.model.NivelAcesso
import grupo9.eduinovatte.service.NivelAcessoRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/niveis-acessos")
class NivelAcessoController (
    val nivelAcessoRepository: NivelAcessoRepository
){
    @GetMapping
    fun buscaNiveisAcesso(): ResponseEntity<List<NivelAcesso>> {
        val niveis = nivelAcessoRepository.findAll()
        if(niveis.isEmpty()) return ResponseEntity.status(204).build()
        return ResponseEntity.status(200).body(niveis)
    }
}