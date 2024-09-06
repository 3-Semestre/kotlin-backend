package grupo9.eduinovatte.domain.service

import grupo9.eduinovatte.domain.model.Permissao
import grupo9.eduinovatte.domain.model.entity.NivelAcesso

interface NivelAcessoService: Permissao {

    override fun validaPermissao(id: Int, condicao: String): Boolean
    fun buscaNiveisAcesso(): List<NivelAcesso>

    fun buscaPorId(id: Int): NivelAcesso
}
