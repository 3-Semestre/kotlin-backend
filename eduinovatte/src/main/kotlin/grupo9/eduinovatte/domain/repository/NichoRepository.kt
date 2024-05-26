package grupo9.eduinovatte.service

import grupo9.eduinovatte.model.Nicho
import grupo9.eduinovatte.model.NivelAcesso
import grupo9.eduinovatte.model.Usuario
import org.springframework.data.jpa.repository.JpaRepository

interface NichoRepository: JpaRepository<Nicho, Int> {
}