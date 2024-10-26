package grupo9.eduinovatte.domain.repository

interface AgendamentoCancelamentoPorMesProjection {
    fun getMes_Ano(): String;
    fun getTaxa_Cancelamento(): Double
}