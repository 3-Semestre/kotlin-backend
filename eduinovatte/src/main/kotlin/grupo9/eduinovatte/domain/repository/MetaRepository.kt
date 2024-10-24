package grupo9.eduinovatte.domain.repository

import grupo9.eduinovatte.domain.model.entity.Meta
import grupo9.eduinovatte.domain.model.entity.Usuario
import grupo9.eduinovatte.domain.model.entity.UsuarioNicho
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import java.util.*

interface MetaRepository : JpaRepository <Meta, Int> {
    fun findByUsuarioId(id: Int): Optional<Meta>
    @Modifying
    @Transactional
    @Query("DELETE FROM Meta u WHERE u.usuario.id = :id")
    fun deletaUsuarioPeloId(id: Int): Int
}