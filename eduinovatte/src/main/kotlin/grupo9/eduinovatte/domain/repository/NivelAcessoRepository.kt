package grupo9.eduinovatte.service

import grupo9.eduinovatte.domain.model.entity.NivelAcesso
import org.springframework.data.jpa.repository.JpaRepository

interface NivelAcessoRepository: JpaRepository<NivelAcesso, Int> {
}