package grupo9.eduinovatte.application.dto.response

import java.time.LocalDate

interface AlunosQuantidadeRepository {
    fun getMes(): String?
    fun getQuantidade_Alunos_Atendidos(): Long?
}