package grupo9.eduinovatte.model.enums

enum class NivelAcessoNome(val descricao: String) {
    REPRESENTANTE_LEGAL("Dono"),
    PROFESSOR_AUXILIAR("Professores"),
    ALUNO("Aluno");

    override fun toString(): String {
        return descricao
    }
}