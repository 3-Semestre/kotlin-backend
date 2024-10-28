package grupo9.eduinovatte.domain.repository

interface AgendamentoConclusaoMesAtualProjection {
    fun getMes(): String;
    fun getQuantidade_Aulas_Concluidas(): Int?
}