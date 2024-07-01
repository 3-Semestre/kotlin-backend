package grupo9.eduinovatte.application.dto.response
interface AgendamentoProximosProjection {
    fun getId_Agendamento(): Long?
    fun getData_Agendamento(): String?
    fun getHorario_Inicio_Agendamento(): String?
    fun getHorario_Fim_Agendamento(): String?
    fun getAssunto_Agendamento(): String?
    fun getFk_Professor(): String?
    fun getFk_Aluno(): String?
    fun getFk_Status(): String?
    fun getFk_NomeCompleto(): String?
}
