package grupo9.eduinovatte.domain.repository

import grupo9.eduinovatte.model.Agendamento
import org.springframework.data.jpa.repository.JpaRepository

interface AgendamentoRepository : JpaRepository<Agendamento, Int>  {
}