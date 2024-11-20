package grupo9.eduinovatte.domain.repository

import grupo9.eduinovatte.domain.model.entity.Agendamento
import grupo9.eduinovatte.domain.repository.projection.AgendamentosDetalhesListagemProjection
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.time.LocalDate
import java.time.LocalTime

interface AgendamentoRepository : JpaRepository<Agendamento, Int> {
    @Query("SELECT a FROM Agendamento a JOIN FETCH a.historico WHERE a.aluno.id = :userId")
    fun findAgendamentosByFkAluno(userId: Int, pageable: Pageable): Page<Agendamento>

    @Query("SELECT a FROM Agendamento a JOIN FETCH a.historico WHERE a.aluno.id = :userId AND MONTH(a.data) = :mes AND YEAR(a.data) = :ano")
    fun findAgendamentosByFkAluno(userId: Int, mes: Int, ano: Int): List<Agendamento>

    @Query("SELECT a FROM Agendamento a JOIN FETCH a.historico WHERE a.professor.id = :userId")
    fun findAgendamentosByFkProfessor(userId: Int, pageable: Pageable): Page<Agendamento>

    @Query("SELECT a FROM Agendamento a JOIN FETCH a.historico WHERE a.professor.id = :userId AND MONTH(a.data) = :mes AND YEAR(a.data) = :ano")
    fun findAgendamentosByFkProfessor(userId: Int, mes: Int, ano: Int): List<Agendamento>

    @Query(
        value = """
    SELECT * FROM agendamentos_detalhes 
    WHERE 
        (FIND_IN_SET(3, status_list) OR FIND_IN_SET(4, status_list) OR FIND_IN_SET(5, status_list)) 
        AND fk_aluno = :userId
        AND (:nome IS NULL OR nome_professor LIKE CONCAT(:nome, '%'))
        AND (:dataInicio IS NULL OR data >= :dataInicio)
        AND (:dataFim IS NULL OR data <= :dataFim)
        AND (:horarioInicio IS NULL OR horario_inicio >= :horarioInicio)
        AND (:horarioFim IS NULL OR horario_fim <= :horarioFim)
    """, nativeQuery = true
    )
    fun findAgendamentosPassadosByFkAluno(
        userId: Int,
        nome: String?,
        dataInicio: LocalDate?,
        dataFim: LocalDate?,
        horarioInicio: LocalTime?,
        horarioFim: LocalTime?,
        pageable: Pageable
    ): Page<AgendamentosDetalhesListagemProjection>

    @Query(
        value = """
    SELECT * FROM agendamentos_detalhes 
    WHERE 
        NOT FIND_IN_SET(3, status_list) AND NOT FIND_IN_SET(4, status_list) AND NOT FIND_IN_SET(5, status_list) 
        AND fk_aluno = :userId
        AND (:nome IS NULL OR nome_professor LIKE CONCAT(:nome, '%'))
        AND (:dataInicio IS NULL OR data >= :dataInicio)
        AND (:dataFim IS NULL OR data <= :dataFim)
        AND (:horarioInicio IS NULL OR horario_inicio >= :horarioInicio)
        AND (:horarioFim IS NULL OR horario_fim <= :horarioFim)
    """, nativeQuery = true
    )
    fun findAgendamentosFuturoByFkAluno(
        userId: Int,
        nome: String?,
        dataInicio: LocalDate?,
        dataFim: LocalDate?,
        horarioInicio: LocalTime?,
        horarioFim: LocalTime?,
        pageable: Pageable
    ): Page<AgendamentosDetalhesListagemProjection>

    @Query(
        value = """
    SELECT * FROM agendamentos_detalhes 
    WHERE 
        (FIND_IN_SET(3, status_list) OR FIND_IN_SET(4, status_list) OR FIND_IN_SET(5, status_list)) 
        AND fk_professor = :userId
        AND (:nome IS NULL OR nome_aluno LIKE CONCAT(:nome, '%'))
        AND (:dataInicio IS NULL OR data >= :dataInicio)
        AND (:dataFim IS NULL OR data <= :dataFim)
        AND (:horarioInicio IS NULL OR horario_inicio >= :horarioInicio)
        AND (:horarioFim IS NULL OR horario_fim <= :horarioFim)
    """, nativeQuery = true
    )
    fun findAgendamentosPassadosByFkProfessor(
        userId: Int,
        nome: String?,
        dataInicio: LocalDate?,
        dataFim: LocalDate?,
        horarioInicio: LocalTime?,
        horarioFim: LocalTime?,
        pageable: Pageable
    ): Page<AgendamentosDetalhesListagemProjection>

    @Query(
        value = """
    SELECT * FROM agendamentos_detalhes 
    WHERE 
        NOT FIND_IN_SET(3, status_list) AND NOT FIND_IN_SET(4, status_list) AND NOT FIND_IN_SET(5, status_list) 
        AND fk_professor = :userId
        AND (:nome IS NULL OR nome_aluno LIKE CONCAT(:nome, '%'))
        AND (:dataInicio IS NULL OR data >= :dataInicio)
        AND (:dataFim IS NULL OR data <= :dataFim)
        AND (:horarioInicio IS NULL OR horario_inicio >= :horarioInicio)
        AND (:horarioFim IS NULL OR horario_fim <= :horarioFim)
    """, nativeQuery = true
    )
    fun findAgendamentosFuturoByFkProfessor(
        userId: Int,
        nome: String?,
        dataInicio: LocalDate?,
        dataFim: LocalDate?,
        horarioInicio: LocalTime?,
        horarioFim: LocalTime?,
        pageable: Pageable
    ): Page<AgendamentosDetalhesListagemProjection>
}

