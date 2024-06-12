package grupo9.eduinovatte.application.dto.response
interface AgendamentoAlunoProjection {
    fun getIdAgendamento(): Long?
    fun getData(): String?
    fun getDia_Semana(): String?
    fun getHorario_Inicio(): String?
    fun getHorario_Fim(): String?
    fun getProfessor_Nome(): String?
    fun getAluno_Nome(): String?
}
