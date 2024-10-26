package grupo9.eduinovatte.domain.repository

interface AgendamentoConclusaoPorMesProjection {
    fun getMes(): String;
    fun getQuantidade_Aulas_Concluidas(): Int?
}