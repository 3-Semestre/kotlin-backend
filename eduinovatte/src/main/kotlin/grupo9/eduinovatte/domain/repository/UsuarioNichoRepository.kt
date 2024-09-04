package grupo9.eduinovatte.service

import grupo9.eduinovatte.domain.model.entity.UsuarioNicho
import org.springframework.data.jpa.repository.JpaRepository

interface UsuarioNichoRepository : JpaRepository <UsuarioNicho, Int> {
    fun findByNichoId(id: Int): List<UsuarioNicho>
    fun findByUsuarioId(id: Int): List<UsuarioNicho>
}