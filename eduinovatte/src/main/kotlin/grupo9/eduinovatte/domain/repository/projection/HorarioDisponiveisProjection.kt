package grupo9.eduinovatte.domain.repository.projection

import java.time.LocalTime

interface HorarioDisponiveisProjection {
    fun getNome_completo(): String;
    fun getHorario_inicio(): LocalTime;
    fun getHorario_fim(): LocalTime;
}