package grupo9.eduinovatte.domain.repository

import grupo9.eduinovatte.model.NivelIngles
import grupo9.eduinovatte.model.UsuarioNicho
import org.springframework.data.jpa.repository.JpaRepository

interface NivelInglesRepository : JpaRepository <UsuarioNicho, Int> {
}