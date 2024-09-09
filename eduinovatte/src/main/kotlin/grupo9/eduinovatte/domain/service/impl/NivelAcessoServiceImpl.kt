package grupo9.eduinovatte.domain.service.impl

import grupo9.eduinovatte.domain.model.entity.NivelAcesso
import grupo9.eduinovatte.domain.service.NivelAcessoService
import grupo9.eduinovatte.service.NivelAcessoRepository
import org.springframework.http.HttpStatusCode
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class NivelAcessoServiceImpl(
    private val nivelAcessoRepository: NivelAcessoRepository
) : NivelAcessoService {

    override fun validaPermissao(id: Int, condicao: String): Boolean {
        val nivelAcesso = buscaPorId(id)

        if (nivelAcesso.nome!!.name !== condicao) {
            return false
        }

        return true
    }
    override fun buscaNiveisAcesso(): List<NivelAcesso> {
        return nivelAcessoRepository.findAll()
    }

    override fun buscaPorId(id: Int): NivelAcesso {
        return nivelAcessoRepository.findById(id).orElseThrow {
            ResponseStatusException(HttpStatusCode.valueOf(404), "Nível de acesso não encontrado")
        }
    }
}
