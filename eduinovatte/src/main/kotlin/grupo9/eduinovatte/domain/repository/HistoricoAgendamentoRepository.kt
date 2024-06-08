package grupo9.eduinovatte.domain.repository

import grupo9.eduinovatte.domain.model.HistoricoAgendamento
import org.springframework.data.jpa.repository.JpaRepository

interface HistoricoAgendamentoRepository : JpaRepository<HistoricoAgendamento, Int>  {
}