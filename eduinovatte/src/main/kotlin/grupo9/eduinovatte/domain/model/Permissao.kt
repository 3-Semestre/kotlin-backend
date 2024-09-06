package grupo9.eduinovatte.domain.model

import grupo9.eduinovatte.domain.model.entity.NivelAcesso
import grupo9.eduinovatte.domain.service.NivelAcessoService
import grupo9.eduinovatte.model.enums.NivelAcessoNome
import grupo9.eduinovatte.model.enums.SituacaoNome

interface Permissao {
    fun validaPermissao(id: Int, condicao: String): Boolean

}
