package grupo9.eduinovatte.domain.repository

import grupo9.eduinovatte.application.dto.response.*
import grupo9.eduinovatte.domain.model.Andamento
import grupo9.eduinovatte.model.Agendamento
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface AgendamentoRepository : JpaRepository<Agendamento, Int> {
    @Query("SELECT a FROM Agendamento a JOIN FETCH a.historico WHERE a.professor.id = :userId OR a.aluno.id = :userId")
    fun findAgendamentosByUserId(userId: Int): List<Agendamento>
    @Query(value = "SELECT * FROM proximos_tres_agendamento_P;", nativeQuery = true)
    fun buscarUltimos3AgendamentosProfessor(): List<AgendamentoAlunoProjection>?

    @Query(value = "SELECT * FROM qtd_agendamento_mes;", nativeQuery = true)
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
    @Query(value = "SELECT * FROM proximos_agendamentos;", nativeQuery = true)
    fun proximosAgendamentosProfessor(): List<AgendamentoProximosProjection>?

    @Query(value = "SELECT * FROM agendamentos_passados;", nativeQuery = true)
    fun agendamentosPassadosProfessor(): List<AgendamentoProximosProjection>?
    @Query(value = "SELECT * FROM todos_professores;", nativeQuery = true)
    fun todosProfessores(): List<AgendamentoAlunoProjection>?
    @Query(value = "SELECT * FROM todos_alunos;", nativeQuery = true)
    fun todos_alunos(): List<AgendamentoAlunoProjection>?
    @Query(value = "SELECT * FROM proximos_tres_agendamento_A;", nativeQuery = true)
    fun buscarUltimos3AgendamentosAluno(): List<AgendamentoAlunoProjection>?
    @Query(value = "SELECT * FROM visao_por_mes;", nativeQuery = true)
    fun buscarVisaoPorMesAluno(): List<AgendamentoVisaoRepository>?
    @Query(value = "SELECT * FROM top_tres_meses;", nativeQuery = true)
    fun buscarTop3MesesAluno(): List<AgendamentoVisaoRepository>?
    @Query(value = "SELECT * FROM qtd_agendamento_mes;", nativeQuery = true)
    fun buscarListaAgendamentoAluno(): List<Andamento>?
    @Query(value = "SELECT * FROM historico_agendamento;", nativeQuery = true)
    fun listaHistoricoAgendamentoAluno(): List<Andamento>?
}



//    @Query("SELECT a FROM Agendamento a WHERE a.professor.id = :userId OR a.aluno.id = :userId")

