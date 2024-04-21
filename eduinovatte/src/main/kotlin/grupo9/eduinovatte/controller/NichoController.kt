package grupo9.eduinovatte.controller

import grupo9.eduinovatte.model.Nicho
import grupo9.eduinovatte.model.NivelAcesso
import grupo9.eduinovatte.service.NichoRepository
import grupo9.eduinovatte.service.NivelAcessoRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/nichos")
class NichoController (
    val nichoRepository: NichoRepository
){
    @GetMapping
    fun buscaNichos(): ResponseEntity<List<Nicho>> {
        val nichos = nichoRepository.findAll()
        if(nichos.isEmpty()) return ResponseEntity.status(204).build()
        return ResponseEntity.status(200).body(nichos)
    }
}