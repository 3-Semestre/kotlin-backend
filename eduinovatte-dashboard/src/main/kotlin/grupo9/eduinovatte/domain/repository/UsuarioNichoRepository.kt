package grupo9.eduinovatte.service

import grupo9.eduinovatte.model.Nicho
import grupo9.eduinovatte.model.UsuarioNicho
import grupo9.eduinovatte.model.enums.NichoNome
import grupo9.eduinovatte.model.enums.NivelInglesNome
import org.springframework.data.jpa.repository.JpaRepository

interface UsuarioNichoRepository : JpaRepository <UsuarioNicho, Int> {
    fun findByNichoId(id: Int): List<UsuarioNicho>
    fun findByUsuarioId(id: Int): List<UsuarioNicho>
}