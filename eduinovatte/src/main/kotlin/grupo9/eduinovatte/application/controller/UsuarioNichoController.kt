package grupo9.eduinovatte.controller

import grupo9.eduinovatte.model.UsuarioNicho
import grupo9.eduinovatte.model.enums.NichoNome
import grupo9.eduinovatte.model.enums.NivelInglesNome
import grupo9.eduinovatte.service.UsuarioNichoRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/usuario-nicho")
class UsuarioNichoController(
    val usuarioNichoRepository: UsuarioNichoRepository
) {

    @GetMapping("/nicho/{nichoNome}")
    fun buscarUsuarioNicho(@PathVariable nichoNome: String): ResponseEntity<List<UsuarioNicho>> {
        val nichoEnum = try {
            NichoNome.valueOf(nichoNome.toUpperCase())
        } catch (e: IllegalArgumentException) {
            return ResponseEntity.status(400).build() // Retorna erro 400 se o enum não for encontrado
        }

        val usuarios = usuarioNichoRepository.findByNichoNome(nichoEnum)
        return if (usuarios.isEmpty()) {
            ResponseEntity.status(204).build()
        } else {
            ResponseEntity.status(200).body(usuarios)
        }
    }

    @GetMapping("/nivel-ingles/{nivelInglesNome}")
    fun buscarUsuarioNivelIngles(@PathVariable nivelInglesNome: String): ResponseEntity<List<UsuarioNicho>> {
        val inglesEnum = try {
            NivelInglesNome.valueOf(nivelInglesNome.toUpperCase())
        } catch (e: IllegalArgumentException) {
            return ResponseEntity.status(400).build() // Retorna erro 400 se o enum não for encontrado
        }

        val usuarios = usuarioNichoRepository.findByNivelInglesNome(inglesEnum)
        return if (usuarios.isEmpty()) {
            ResponseEntity.status(204).build()
        } else {
            ResponseEntity.status(200).body(usuarios)
        }
    }
}

