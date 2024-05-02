package grupo9.eduinovatte.service

import grupo9.eduinovatte.model.Nicho
import grupo9.eduinovatte.model.UsuarioNicho
import grupo9.eduinovatte.model.enums.NichoNome
import org.springframework.data.jpa.repository.JpaRepository

interface UsuarioNichoRepository : JpaRepository <UsuarioNicho, Int> {

   // fun findByNichoContains(nicho: NichoNome): List<UsuarioNicho>
}