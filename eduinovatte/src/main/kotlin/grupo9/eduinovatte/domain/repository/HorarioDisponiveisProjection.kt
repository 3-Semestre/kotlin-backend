package grupo9.eduinovatte.domain.repository

import java.time.LocalDate
import java.time.LocalTime

interface HorarioDisponiveisProjection {
    fun getNome_completo(): String;
    fun getHorario_inicio(): LocalTime;
    fun getHorario_fim(): LocalTime;
}