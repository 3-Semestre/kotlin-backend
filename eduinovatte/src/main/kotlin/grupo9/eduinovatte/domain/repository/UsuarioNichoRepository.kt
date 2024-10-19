package grupo9.eduinovatte.service

import grupo9.eduinovatte.domain.model.entity.Usuario
import grupo9.eduinovatte.domain.model.entity.UsuarioNicho
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import java.util.*

interface UsuarioNichoRepository : JpaRepository <UsuarioNicho, Int> {
    fun findByNichoId(id: Int): List<UsuarioNicho>
    fun findByUsuarioId(id: Int): List<UsuarioNicho>
    @Modifying
    @Transactional
    @Query("DELETE FROM UsuarioNicho u WHERE u.usuario.id = :id")
    fun deletaUsuarioPeloId(id: Int): Int
    fun findByUsuarioIdAndNichoId(idUsuario: Int, idNicho: Int): Optional<UsuarioNicho>
}