package grupo9.eduinovatte.domain.repository

import grupo9.eduinovatte.domain.model.UsuarioNivelIngles
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface UsuarioNivelInglesRepository : JpaRepository <UsuarioNivelIngles, Int> {
    fun findByNivelInglesId(id: Int): List<UsuarioNivelIngles>

    fun findByUsuarioId(id: Int): List<UsuarioNivelIngles>
}