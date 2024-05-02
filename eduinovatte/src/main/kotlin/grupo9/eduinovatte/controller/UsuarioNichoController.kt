package grupo9.eduinovatte.controller

import grupo9.eduinovatte.model.UsuarioNicho
import grupo9.eduinovatte.model.enums.NichoNome
import grupo9.eduinovatte.service.UsuarioNichoRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/usuario-nicho")
class UsuarioNichoController (val usuarioNichoRepository: UsuarioNichoRepository) {

//    @GetMapping("/nicho/{nichoNome}")
//    fun buscarUsuarioNicho(@PathVariable nichoNome: NichoNome)
//            : ResponseEntity<List<UsuarioNicho>> {
//        val nicho = NichoNome.valueOf(nichoNome.name)
//
//         val usuarios = usuarioNichoRepository.findByNichoContains(nicho)
//        if (usuarios.isEmpty()) return ResponseEntity.status(204).build()
//        return ResponseEntity.status(200).body(nicho)
//
//    }

}