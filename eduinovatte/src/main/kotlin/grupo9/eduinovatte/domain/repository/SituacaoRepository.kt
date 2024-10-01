package grupo9.eduinovatte.service

import grupo9.eduinovatte.domain.model.entity.Situacao
import org.springframework.data.jpa.repository.JpaRepository

interface SituacaoRepository: JpaRepository<Situacao, Int> {
}