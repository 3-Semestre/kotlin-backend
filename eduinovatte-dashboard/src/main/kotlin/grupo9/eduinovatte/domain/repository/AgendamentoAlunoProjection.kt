package grupo9.eduinovatte.application.dto.response
interface AgendamentoAlunoProjection {
    fun getId_Agendamento(): Long?
    fun getData(): String?
    fun getDia_Semana(): String?
    fun getHorario_Inicio(): String?
    fun getHorario_Fim(): String?
    fun getProfessor_Nome(): String?
    fun getAluno_Nome(): String?
}
