package grupo9.eduinovatte.application.dto.response
interface AgendamentoVisaoRepository {
    fun getMes(): String?
    fun getQuantidade_Aulas_Concluidas(): Long?
}