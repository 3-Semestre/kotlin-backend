package grupo9.eduinovatte.application.dto.request

import org.jetbrains.annotations.NotNull

data class UsuarioNivelRequest (
    @NotNull
    val usuarioId: Int,
    val nivelSelecionados: List<Int>,
    val nivelNaoSelecionados: List<Int>
)