package grupo9.eduinovatte.service

import grupo9.eduinovatte.domain.model.entity.Nicho
import org.springframework.data.jpa.repository.JpaRepository

interface NichoRepository: JpaRepository<Nicho, Int> {
}