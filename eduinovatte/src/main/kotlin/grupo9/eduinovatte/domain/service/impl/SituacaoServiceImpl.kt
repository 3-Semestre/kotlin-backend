package grupo9.eduinovatte.domain.service.impl

import grupo9.eduinovatte.domain.model.entity.Situacao
import grupo9.eduinovatte.domain.service.SituacaoService
import grupo9.eduinovatte.domain.service.UsuarioService
import grupo9.eduinovatte.model.enums.SituacaoNome
import grupo9.eduinovatte.service.SituacaoRepository
import org.springframework.http.HttpStatusCode
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class SituacaoServiceImpl(
    val situacaoRepository: SituacaoRepository
): SituacaoService {

    override fun validaPermissao(id: Int, condicao: String): Boolean {
        val situacao = buscaPorId(id)

        if (situacao?.nome!!.name !== condicao) {
            return false
        }
        return true
    }
    override fun buscaSituacoes(): List<Situacao>{
        return situacaoRepository.findAll()
    }

    override fun buscaPorId(id: Int): Situacao {
        return situacaoRepository.findById(id).get()
    }
}