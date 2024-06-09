package grupo9.eduinovatte.domain.service

import grupo9.eduinovatte.domain.model.Status
import grupo9.eduinovatte.domain.repository.StatusRepository

import org.springframework.stereotype.Service

@Service
class StatusService(
    val statusRepository: StatusRepository
){
    fun buscaStatus(): List<Status>{
        return statusRepository.findAll()
    }

    fun buscaStatusPorId(id: Int): Status{
        return statusRepository.findById(id).get()
    }
}