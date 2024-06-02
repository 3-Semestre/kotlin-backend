package grupo9.eduinovatte.domain.service

import grupo9.eduinovatte.model.NivelAcesso
import grupo9.eduinovatte.service.NivelAcessoRepository
import org.springframework.stereotype.Service

@Service
class NivelAcessoService(
    val nivelAcessoRepository: NivelAcessoRepository
){
    fun buscaNiveisAcesso(): List<NivelAcesso>{
        return nivelAcessoRepository.findAll()
    }

    fun buscaPorId(id: Int): NivelAcesso{
        return nivelAcessoRepository.findById(id).get()
    }
}