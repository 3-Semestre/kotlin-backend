package grupo9.eduinovatte.application.dto.request

import org.jetbrains.annotations.NotNull

data class UsuarioNichoRequest(
    @NotNull
    val usuarioId: Int,
    val nichoSelecionados: List<Int>,
    val nichoNaoSelecionados: List<Int>
)
