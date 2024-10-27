package grupo9.eduinovatte.domain.service

import grupo9.eduinovatte.application.dto.request.AgendamentoCadastro
import grupo9.eduinovatte.application.dto.request.AgendamentoCadastroRequest
import grupo9.eduinovatte.application.dto.request.AgendamentoTransferenciaRequest
import grupo9.eduinovatte.application.dto.request.FiltroAgendamentoForm
import grupo9.eduinovatte.domain.model.entity.Agendamento
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import grupo9.eduinovatte.domain.repository.projection.AgendamentosDetalhesListagemProjection
import java.util.*

interface AgendamentoService {

    fun validaNivelAcesso(novoAgendamento: Agendamento)

    fun retornaAgendamento(agendamento: Agendamento): AgendamentoCadastro

    fun buscaAgendamentos(): List<Agendamento>

    fun buscaAgendamentoPorId(id: Int): Optional<Agendamento>

    fun buscaAgendamentosUsuario(tipo: Int, id: Int, pageable: Pageable): Page<Agendamento>

    fun buscaAgendamentosUsuarioMes(tipo: Int, id: Int, mes: Int, ano: Int): List<Agendamento>

    fun buscaAgendamentosTempoUsuario(usuario: Int, tempo: String, pageable: Pageable): Page<AgendamentosDetalhesListagemProjection>

    fun salvarAgendamento(novoAgendamento: AgendamentoCadastroRequest): AgendamentoCadastro
    fun transferirAgendamento(agendamento: AgendamentoTransferenciaRequest): AgendamentoCadastro

    fun atualizaAssuntoAgendamentoPorId(id: Int, novoAssunto: String): Agendamento

    fun filtrarAlunoPassado(filtro: FiltroAgendamentoForm, id: Int): List<Agendamento?>
    fun filtrarAlunoFuturo(filtro: FiltroAgendamentoForm, id: Int): List<Agendamento?>

    fun filtrarProfessorPassado(filtro: FiltroAgendamentoForm, id: Int): List<Agendamento?>
    fun filtrarProfessorFuturo(filtro: FiltroAgendamentoForm, id: Int): List<Agendamento?>
}
