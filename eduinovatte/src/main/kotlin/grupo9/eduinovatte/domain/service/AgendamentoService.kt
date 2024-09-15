package grupo9.eduinovatte.domain.service

import grupo9.eduinovatte.application.dto.request.AgendamentoCadastro
import grupo9.eduinovatte.application.dto.request.FiltroAgendamentoForm
import grupo9.eduinovatte.application.dto.request.FiltroForm
import grupo9.eduinovatte.domain.model.entity.Agendamento
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import grupo9.eduinovatte.domain.model.entity.Usuario
import java.util.*

interface AgendamentoService {

    fun validaNivelAcesso(novoAgendamento: Agendamento)

    fun retornaAgendamento(agendamento: Agendamento): AgendamentoCadastro

    fun buscaAgendamentos(): List<Agendamento>

    fun buscaAgendamentoPorId(id: Int): Optional<Agendamento>

    fun buscaAgendamentosUsuario(tipo: Int, id: Int, pageable: Pageable): Page<Agendamento>

    fun salvarAgendamento(novoAgendamento: Agendamento): AgendamentoCadastro

    fun filtrarAluno(filtro: FiltroAgendamentoForm, id: Int): List<Agendamento>?

    fun filtrarProfessor(filtro: FiltroAgendamentoForm, id: Int): List<Agendamento>?
}
