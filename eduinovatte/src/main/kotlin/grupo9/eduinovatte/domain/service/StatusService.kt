package grupo9.eduinovatte.domain.service

import grupo9.eduinovatte.domain.model.entity.Status
import grupo9.eduinovatte.domain.repository.StatusRepository

import org.springframework.stereotype.Service


interface StatusService {

    fun buscaStatus(): List<Status>

    fun buscaStatusPorId(id: Int): Status
}
