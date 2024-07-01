package grupo9.eduinovatte.domain.repository

interface AgendamentoCancelamentoPorMesProjection {
    fun getMes(): String;
    fun getTaxa_Cancelamento(): Float
}