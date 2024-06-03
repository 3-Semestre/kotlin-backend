package grupo9.eduinovatte.domain.repository

import grupo9.eduinovatte.model.HorarioProfessor
import grupo9.eduinovatte.model.Nicho
import org.springframework.data.jpa.repository.JpaRepository

interface HorarioProfessorRepository: JpaRepository<HorarioProfessor, Int> {

}