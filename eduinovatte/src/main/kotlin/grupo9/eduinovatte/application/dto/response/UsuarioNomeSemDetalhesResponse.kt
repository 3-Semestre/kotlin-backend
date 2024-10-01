package grupo9.eduinovatte.application.dto.response

import grupo9.eduinovatte.domain.model.entity.NivelAcesso

data class UsuarioNomeSemDetalhesResponse (
    var id: Int? = null,
    var nomeCompleto: String? = null,
    var cpf: String? = null,
    var nivelAcesso: NivelAcesso? = null
)
