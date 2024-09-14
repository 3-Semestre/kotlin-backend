package grupo9.eduinovatte.domain.repository

import grupo9.eduinovatte.domain.model.entity.Agendamento
import grupo9.eduinovatte.domain.model.entity.Usuario
import grupo9.eduinovatte.model.enums.NichoNome
import grupo9.eduinovatte.model.enums.NivelInglesNome
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.LocalDate
import java.time.LocalTime

interface AgendamentoRepository : JpaRepository<Agendamento, Int> {
    @Query("SELECT a FROM Agendamento a JOIN FETCH a.historico WHERE a.aluno.id = :userId")
    fun findAgendamentosByFkAluno(userId: Int): List<Agendamento>


    @Query("SELECT a FROM Agendamento a JOIN FETCH a.historico WHERE a.professor.id = :userId")
    fun findAgendamentosByFkProfessor(userId: Int): List<Agendamento>

    @Query("""
    SELECT a FROM Agendamento a
    JOIN Usuario u ON a.aluno.id = :id
    WHERE (:nome IS NULL OR u.nomeCompleto LIKE %:nome%)
      AND (:data_inicio IS NULL OR :data_fim IS NULL OR a.data BETWEEN :data_inicio AND :dataFim)
      AND (:horario_inicio IS NULL OR :horario_fim IS NULL OR (a.horarioInicio >= :horario_inicio AND a.horarioFim <= :horario_fim))
""")
    fun filtrarAluno(nome: String?, data_inicio: LocalDate?, data_fim: LocalDate?, horario_inicio: LocalTime?, horario_fim: LocalTime?, id: Int): List<Agendamento>?

    @Query("""
    SELECT a FROM Agendamento a
    JOIN Usuario u ON a.professor.id = :id
    WHERE (:nome IS NULL OR u.nomeCompleto LIKE %:nome%)
      AND (:data_inicio IS NULL OR :dataFim IS NULL OR a.data BETWEEN :data_inicio AND :data_fim)
      AND (:horario_inicio IS NULL OR :horario_fim IS NULL OR (a.horarioInicio >= :horario_inicio AND a.horarioFim <= :horario_fim))

""")
    fun filtrarProfessor(nome: String?, data_inicio: LocalDate?, data_fim: LocalDate?, horario_inicio: LocalTime?, horario_fim: LocalTime?, id: Int): List<Agendamento>?
}



//    @Query("SELECT a FROM Agendamento a WHERE a.professor.id = :userId OR a.aluno.id = :userId")

