package grupo9.eduinovatte.controller

import grupo9.eduinovatte.application.dto.response.UsuarioResponse
import grupo9.eduinovatte.model.Usuario
import grupo9.eduinovatte.model.UsuarioNicho
import grupo9.eduinovatte.model.enums.NichoNome
import grupo9.eduinovatte.model.enums.NivelInglesNome
import grupo9.eduinovatte.service.UsuarioNichoRepository
import grupo9.eduinovatte.service.UsuarioRepository
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.*


@Service
class UsuarioNichoService(
    val usuarioNichoRepository: UsuarioNichoRepository
) {

    fun salvar(novoUsuarioNicho: UsuarioNicho): UsuarioNicho {
        val usuarioNicho = usuarioNichoRepository.save(novoUsuarioNicho)

        return usuarioNicho
    }

    fun buscaPorNicho(id: Int): List<UsuarioNicho> {
        val usuarioNichos = usuarioNichoRepository.findByNichoId(id)

        return usuarioNichos
    }

    fun buscaPorIdUsuario(id: Int): List<UsuarioNicho> {
        val usuarioNichos = usuarioNichoRepository.findByUsuarioId(id)

        return usuarioNichos
    }
    fun deleta(id: Int) {
        usuarioNichoRepository.deleteById(id)
    }

}

