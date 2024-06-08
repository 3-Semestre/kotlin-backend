package grupo9.eduinovatte.service

import grupo9.eduinovatte.model.NivelAcesso
import grupo9.eduinovatte.model.Situacao
import grupo9.eduinovatte.model.Usuario
import org.springframework.data.jpa.repository.JpaRepository

interface SituacaoRepository: JpaRepository<Situacao, Int> {
}