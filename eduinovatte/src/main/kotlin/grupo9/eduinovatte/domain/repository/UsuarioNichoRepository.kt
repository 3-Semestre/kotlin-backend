package grupo9.eduinovatte.service

import grupo9.eduinovatte.domain.model.entity.Usuario
import grupo9.eduinovatte.domain.model.entity.UsuarioNicho
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface UsuarioNichoRepository : JpaRepository <UsuarioNicho, Int> {
    fun findByNichoId(id: Int): List<UsuarioNicho>
    fun findByUsuarioId(id: Int): List<UsuarioNicho>
    fun deleteByUsuarioId(id: Int): Int
}