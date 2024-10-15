package grupo9.eduinovatte.domain.repository

import grupo9.eduinovatte.domain.model.entity.HorarioProfessor
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.query.Procedure
import java.time.LocalDate

interface HorarioProfessorRepository : JpaRepository<HorarioProfessor, Int> {

    fun findByUsuarioId(id: Int): HorarioProfessor

    @Procedure(procedureName = "sp_horarios_disponiveis")
    fun findHorariosDisponiveis(dia: LocalDate, id: Int): List<HorarioDisponiveisProjection>

}
