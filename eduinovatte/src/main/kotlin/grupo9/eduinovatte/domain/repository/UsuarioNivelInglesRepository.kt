package grupo9.eduinovatte.domain.repository

import grupo9.eduinovatte.domain.model.entity.UsuarioNivelIngles
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query

interface UsuarioNivelInglesRepository : JpaRepository <UsuarioNivelIngles, Int> {
    fun findByNivelInglesId(id: Int): List<UsuarioNivelIngles>
    @Modifying
    @Transactional
    @Query("DELETE FROM UsuarioNivelIngles u WHERE u.usuario.id = :id")
    fun deletaUsuarioPeloId(id: Int): Int
    fun findByUsuarioId(id: Int): List<UsuarioNivelIngles>
}