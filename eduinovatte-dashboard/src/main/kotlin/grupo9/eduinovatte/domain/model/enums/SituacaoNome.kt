package grupo9.eduinovatte.model.enums

enum class SituacaoNome(val descricao: String) {
    ATIVO("Conectado na plataforma."),
    INATIVO("Desconectado da plataforma.");

    override fun toString(): String {
        return descricao
    }
}