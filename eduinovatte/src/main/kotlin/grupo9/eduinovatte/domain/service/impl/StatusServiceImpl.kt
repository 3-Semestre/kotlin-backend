package grupo9.eduinovatte.domain.service.impl

import grupo9.eduinovatte.domain.model.entity.Status
import grupo9.eduinovatte.domain.repository.StatusRepository
import grupo9.eduinovatte.domain.service.StatusService
import org.springframework.stereotype.Service

@Service
class StatusServiceImpl(
    val statusRepository: StatusRepository
) : StatusService {

    override fun buscaStatus(): List<Status> {
        return statusRepository.findAll()
    }

    override fun buscaStatusPorId(id: Int): Status {
        return statusRepository.findById(id).get()
    }
}
