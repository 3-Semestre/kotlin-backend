package grupo9.eduinovatte.domain.repository

import grupo9.eduinovatte.domain.model.entity.Agendamento
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface AgendamentoRepository : JpaRepository<Agendamento, Int> {
    @Query("SELECT a FROM Agendamento a JOIN FETCH a.historico WHERE a.aluno.id = :userId")
    fun findAgendamentosByFkAluno(userId: Int, pageable: Pageable): Page<Agendamento>

    @Query("SELECT a FROM Agendamento a JOIN FETCH a.historico WHERE a.professor.id = :userId")
    fun findAgendamentosByFkProfessor(userId: Int, pageable: Pageable): Page<Agendamento>
}



//    @Query("SELECT a FROM Agendamento a WHERE a.professor.id = :userId OR a.aluno.id = :userId")

