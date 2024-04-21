package grupo9.eduinovatte.service

import grupo9.eduinovatte.model.Usuario
import org.springframework.data.jpa.repository.JpaRepository

interface UsuarioRepository: JpaRepository<Usuario, Int> {
    fun findByNivelAcessoId(id: Int): List<Usuario>
}