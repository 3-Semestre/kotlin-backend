package grupo9.eduinovatte.domain.service

import grupo9.eduinovatte.domain.model.entity.NivelAcesso

interface NivelAcessoService {

    fun buscaNiveisAcesso(): List<NivelAcesso>

    fun buscaPorId(id: Int): NivelAcesso
}
