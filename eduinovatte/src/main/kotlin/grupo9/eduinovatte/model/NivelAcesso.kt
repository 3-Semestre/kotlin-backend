package grupo9.eduinovatte.model

enum class NivelAcesso(val descricao: String) {
    REPRESENTANTE_LEGAL("Dono"),
    PROFESSOR_AUXILIAR("Professores");

    override fun toString(): String {
        return descricao
    }
}