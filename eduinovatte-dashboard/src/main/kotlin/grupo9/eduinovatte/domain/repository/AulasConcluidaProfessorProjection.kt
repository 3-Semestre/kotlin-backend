package grupo9.eduinovatte.domain.repository

interface AulasConcluidaProfessorProjection {
    fun getMes(): String
    fun getQuantidade_aulas_concluidas(): Int
}