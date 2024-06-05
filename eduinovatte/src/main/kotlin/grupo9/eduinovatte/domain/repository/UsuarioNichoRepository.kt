package grupo9.eduinovatte.service

import grupo9.eduinovatte.model.Nicho
import grupo9.eduinovatte.model.UsuarioNicho
import grupo9.eduinovatte.model.enums.NichoNome
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface UsuarioNichoRepository : JpaRepository <UsuarioNicho, Int> {
    fun findByNichoNome(nichoNome: NichoNome): List<UsuarioNicho>
}