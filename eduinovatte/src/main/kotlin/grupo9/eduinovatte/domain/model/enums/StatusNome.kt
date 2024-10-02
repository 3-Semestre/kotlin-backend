package grupo9.eduinovatte.domain.model.enums

enum class StatusNome(val descricao: String) {
    CANCELADO("Agendamento foi cancelado."),
    PENDENTE("Agendamento está aguardando confirmação do professor."),
    CONFIRMADO("Agendamento foi confirmado pelo professor."),
    CONCLUIDO("Agendamento foi concluido."),
    TRANSFERIDO("Agendamento foi transferido.");

    override fun toString(): String {
        return descricao
    }
}