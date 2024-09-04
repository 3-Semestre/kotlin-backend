package grupo9.eduinovatte.domain.repository

import grupo9.eduinovatte.domain.model.entity.HorarioProfessor
import org.springframework.data.jpa.repository.JpaRepository

interface HorarioProfessorRepository: JpaRepository<HorarioProfessor, Int> {

    fun findByUsuarioId(id:Int): HorarioProfessor
}