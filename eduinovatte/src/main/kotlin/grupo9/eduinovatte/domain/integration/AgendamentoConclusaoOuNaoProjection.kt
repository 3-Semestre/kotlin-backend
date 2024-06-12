package grupo9.eduinovatte.application.dto.response
interface AgendamentoConclusaoOuNaoProjection {
    fun getQtd_Aulas_Concluidas(): Long?
    fun getQtd_Aulas_Nao_concluidas(): Long?
}
