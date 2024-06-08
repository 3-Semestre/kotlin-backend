package grupo9.eduinovatte.application.dto.response

import grupo9.eduinovatte.model.NivelAcesso
import java.time.LocalDate

data class UsuarioNomeSemDetalhesResponse (
    var id: Int? = null,
    var nomeCompleto: String? = null,
    var nivelAcesso: NivelAcesso? = null
)
