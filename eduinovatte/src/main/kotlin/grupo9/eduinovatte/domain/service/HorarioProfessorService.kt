package grupo9.eduinovatte.domain.service

import grupo9.eduinovatte.application.dto.request.HorarioTransferenciaRequest
import grupo9.eduinovatte.application.dto.requestg.HorarioProfessorRequest
import grupo9.eduinovatte.domain.model.entity.HorarioProfessor
import grupo9.eduinovatte.domain.repository.projection.HorarioDisponiveisProjection
import grupo9.eduinovatte.domain.repository.projection.ProfessorDisponivelTransferencia
import java.time.LocalDate

interface HorarioProfessorService {

    fun salvar(novoHorarioProfessor: HorarioProfessor): HorarioProfessor

    fun buscaPorUsuario(id: Int): HorarioProfessor

    fun edita(horario: HorarioProfessor): HorarioProfessor

    fun edita(horario: HorarioProfessorRequest, id: Int): HorarioProfessor

    fun buscaHorarios(): List<HorarioProfessor>

    fun buscaHorariosDisponiveis(dia: LocalDate, id: Int): List<HorarioDisponiveisProjection>

    fun deleta(id: Int)

    fun buscaProfessoresTransferencia(agendamento: HorarioTransferenciaRequest): List<ProfessorDisponivelTransferencia>
}
