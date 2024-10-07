package grupo9.eduinovatte.domain.repository

import grupo9.eduinovatte.domain.model.entity.Agendamento
import grupo9.eduinovatte.domain.model.entity.Usuario
import grupo9.eduinovatte.model.enums.NichoNome
import grupo9.eduinovatte.model.enums.NivelInglesNome
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.time.LocalDate
import java.time.LocalTime

interface AgendamentoRepository : JpaRepository<Agendamento, Int> {
    @Query("SELECT a FROM Agendamento a JOIN FETCH a.historico WHERE a.aluno.id = :userId")
    fun findAgendamentosByFkAluno(userId: Int, pageable: Pageable): Page<Agendamento>

    @Query("SELECT a FROM Agendamento a JOIN FETCH a.historico WHERE a.professor.id = :userId")
    fun findAgendamentosByFkProfessor(userId: Int, pageable: Pageable): Page<Agendamento>

    @Query(value = "SELECT * FROM agendamentos_detalhes WHERE FIND_IN_SET(3, status_list) OR FIND_IN_SET(4, status_list) AND fk_aluno = :userId", nativeQuery = true)
    fun findAgendamentosPassadosByFkAluno(userId: Int, pageable: Pageable): Page<AgendamentosDetalhesListagemResponse>

    @Query(value = "SELECT * FROM agendamentos_detalhes WHERE NOT FIND_IN_SET(3, status_list) AND NOT FIND_IN_SET(4, status_list) AND fk_aluno = :userId", nativeQuery = true)
    fun findAgendamentosFuturoByFkAluno(userId: Int, pageable: Pageable): Page<AgendamentosDetalhesListagemResponse>

    @Query(value = "SELECT * FROM agendamentos_detalhes WHERE FIND_IN_SET(3, status_list) OR FIND_IN_SET(4, status_list) AND fk_professor = :userId", nativeQuery = true)
    fun findAgendamentosPassadosByFkProfessor(userId: Int, pageable: Pageable): Page<AgendamentosDetalhesListagemResponse>

    @Query(value = "SELECT * FROM agendamentos_detalhes WHERE NOT FIND_IN_SET(4, status_list) AND NOT FIND_IN_SET(3, status_list) AND fk_professor = :userId", nativeQuery = true)
    fun findAgendamentosFuturoByFkProfessor(userId: Int, pageable: Pageable): Page<AgendamentosDetalhesListagemResponse>


    @Query("""
    SELECT a FROM Agendamento a
    JOIN Usuario u ON a.aluno.id = :id
    WHERE (:nome IS NULL OR u.nomeCompleto LIKE %:nome%)
      AND (:data_inicio IS NULL OR :data_fim IS NULL OR a.data BETWEEN :data_inicio AND :dataFim)
      AND (:horario_inicio IS NULL OR :horario_fim IS NULL OR (a.horarioInicio >= :horario_inicio AND a.horarioFim <= :horario_fim))
""")
    fun filtrarAluno(nome: String?, data_inicio: LocalDate?, data_fim: LocalDate?, horario_inicio: LocalTime?, horario_fim: LocalTime?, id: Int): List<Agendamento?>

    @Query("""
    SELECT a FROM Agendamento a
    JOIN Usuario u ON a.professor.id = :id
    WHERE (:nome IS NULL OR u.nomeCompleto LIKE %:nome%)
      AND (:data_inicio IS NULL OR :data_fim IS NULL OR a.data BETWEEN :data_inicio AND :data_fim)
      AND (:horario_inicio IS NULL OR :horario_fim IS NULL OR (a.horarioInicio >= :horario_inicio AND a.horarioFim <= :horario_fim))
      AND (:assunto IS NULL OR a.assunto LIKE %:assunto%)
""")
    fun filtrarProfessor(nome: String?, data_inicio: LocalDate?, data_fim: LocalDate?, horario_inicio: LocalTime?, horario_fim: LocalTime?, assunto: String?, id: Int): List<Agendamento?>
}



//    @Query("SELECT a FROM Agendamento a WHERE a.professor.id = :userId OR a.aluno.id = :userId")

