package grupo9.eduinovatte.domain.repository

import grupo9.eduinovatte.application.dto.response.*
import grupo9.eduinovatte.domain.model.Andamento
import grupo9.eduinovatte.model.Agendamento
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface AgendamentoRepository : JpaRepository<Agendamento, Int> {
    @Query("SELECT a FROM Agendamento a JOIN FETCH a.historico WHERE a.professor.id = :userId OR a.aluno.id = :userId")
    fun findAgendamentosByUserId(userId: Int): List<Agendamento>
    @Query(value = "CALL proximos_tres_agendamento_P(:id);", nativeQuery = true)
    fun buscarUltimos3AgendamentosProfessor(id: Int): List<AgendamentoAlunoProjection>?

    @Query(value = "CALL qtd_agendamento_mes(8, 2024);", nativeQuery = true)
    fun qtdAgendamentoMesProfessor(): Int?

    // TO DO
    @Query(value = "SELECT * FROM qtd_cancelamento_aulas;", nativeQuery = true)
    fun tempoConfirmacaoAgendamentoProfessor(): List<AgendamentoAlunoProjection>?

    @Query(value = "SELECT * FROM qtd_novos_alunos;", nativeQuery = true)
    fun qtdAlunosNovosProfessor(): Int?
    @Query(value = "SELECT * FROM qtd_cancelamento_aulas;", nativeQuery = true)
    fun qtdCancelamentoAulasProfessor(): Int?
    @Query(value = "SELECT * FROM qtd_conclusao_ou_nao;", nativeQuery = true)
    fun qtdConclusaoProfessor(): List<AgendamentoConclusaoOuNaoProjection>?
    @Query(value = "SELECT * FROM taxa_cancelamento;", nativeQuery = true)
    fun taxaCancelamentoProfessor(): Float?
    @Query(value = "select * from taxa_cancelamento_mensal;", nativeQuery = true)
    fun taxaCancelamentoProfessorPorMes(): List<AgendamentoCancelamentoPorMesProjection>?
    @Query(value = "CALL proximos_agendamentos(:id)", nativeQuery = true)
    fun proximosAgendamentosProfessor(id: Int): List<AgendamentoProximosProjection>?

    @Query(value = "CALL proximos_agendamentos_aluno(:id)", nativeQuery = true)
    fun proximosAgendamentosAluno(id: Int): List<AgendamentoProximosProjection>?

    @Query(value = "CALL agendamentos_passados(:id);", nativeQuery = true)
    fun agendamentosPassadosProfessor(id: Int): List<AgendamentoProximosProjection>?

    @Query(value = "CALL agendamentos_passados_aluno(:id);", nativeQuery = true)
    fun agendamentosPassadosAluno(id: Int): List<AgendamentoProximosProjection>?
    @Query(value = "SELECT * FROM todos_professores;", nativeQuery = true)
    fun todosProfessores(): List<AgendamentoAlunoProjection>?
    @Query(value = "SELECT * FROM todos_alunos;", nativeQuery = true)
    fun todos_alunos(): List<AgendamentoAlunoProjection>?
    @Query(value = "CALL proximos_tres_agendamentos(:id);", nativeQuery = true)
    fun buscarUltimos3AgendamentosAluno(id: Int): List<AgendamentoAlunoProjection>?
    @Query(value = "CALL visao_por_mes(:id);", nativeQuery = true)
    fun buscarVisaoPorMesAluno(id: Int): List<AgendamentoVisaoRepository>?
    @Query(value = "CALL top_tres_meses(:id);", nativeQuery = true)
    fun buscarTop3MesesAluno(id: Int): List<AgendamentoVisaoRepository>?
    @Query(value = "CALL qtd_agendamento_mes(8, 2024);", nativeQuery = true)
    fun buscarListaAgendamentoAluno(): List<Andamento>?
    @Query(value = "SELECT * FROM historico_agendamento;", nativeQuery = true)
    fun listaHistoricoAgendamentoAluno(): List<Andamento>?
}



//    @Query("SELECT a FROM Agendamento a WHERE a.professor.id = :userId OR a.aluno.id = :userId")

