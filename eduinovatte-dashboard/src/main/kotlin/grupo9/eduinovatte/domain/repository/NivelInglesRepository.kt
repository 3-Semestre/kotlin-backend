package grupo9.eduinovatte.domain.repository


import grupo9.eduinovatte.model.NivelIngles
import org.springframework.data.jpa.repository.JpaRepository

interface NivelInglesRepository : JpaRepository <NivelIngles, Int> {
}