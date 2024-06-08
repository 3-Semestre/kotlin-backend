package grupo9.eduinovatte.domain.repository

import grupo9.eduinovatte.domain.model.Andamento
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface AndamentoRepository : JpaRepository<Andamento, Int> {
    @Query("SELECT a FROM Andamento a WHERE a.agendamento.id = :agendamentoId ORDER BY a.dataAtualizacao DESC limit 1")
    fun findTopByAgendamentoIdOrderByDataAtualizacaoDesc(agendamentoId: Int): Andamento
}
