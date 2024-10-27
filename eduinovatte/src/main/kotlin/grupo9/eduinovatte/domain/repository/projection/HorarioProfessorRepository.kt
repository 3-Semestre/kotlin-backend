package grupo9.eduinovatte.domain.repository.projection

import grupo9.eduinovatte.application.dto.request.HorarioTransferenciaRequest
import grupo9.eduinovatte.domain.model.entity.HorarioProfessor
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.jpa.repository.query.Procedure
import java.time.LocalDate
import java.time.LocalTime

interface HorarioProfessorRepository : JpaRepository<HorarioProfessor, Int> {

    fun findByUsuarioId(id: Int): HorarioProfessor

    @Procedure(procedureName = "sp_horarios_disponiveis")
    fun findHorariosDisponiveis(dia: LocalDate, id: Int): List<HorarioDisponiveisProjection>

    @Query("CALL sp_professores_disponiveis_transferencia(:dia, :hora_inicio, :hora_fim)", nativeQuery = true)
    fun buscaProfessoresDisponiveis(dia: LocalDate, hora_inicio: LocalTime, hora_fim: LocalTime): List<ProfessorDisponivelTransferencia>
}
