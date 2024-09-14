package grupo9.eduinovatte.domain.service

import grupo9.eduinovatte.application.dto.request.AgendamentoCadastro
import grupo9.eduinovatte.domain.model.entity.Agendamento

interface AgendamentoService {

    fun validaNivelAcesso(novoAgendamento: Agendamento)

    fun retornaAgendamento(agendamento: Agendamento): AgendamentoCadastro

    fun buscaAgendamentos(): List<Agendamento>

    fun buscaAgendamentoPorId(id: Int): Agendamento

    fun buscaAgendamentosUsuario(tipo: Int, id: Int): List<Agendamento?>

    fun salvarAgendamento(novoAgendamento: Agendamento): AgendamentoCadastro
}
