package grupo9.eduinovatte.domain.service

import grupo9.eduinovatte.domain.model.entity.Status
import grupo9.eduinovatte.domain.repository.StatusRepository

import org.springframework.stereotype.Service
import java.util.*


interface StatusService {

    fun buscaStatus(): List<Status>

    fun buscaStatusPorId(id: Int): Optional<Status>
}
