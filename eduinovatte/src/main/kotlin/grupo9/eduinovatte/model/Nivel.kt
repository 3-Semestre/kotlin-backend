package grupo9.eduinovatte.model

enum class Nivel(val descricao: String) {
    REPRESENTANTE_LEGAL("Dono"),
    PROFESSOR_AUXILIAR("Professores"),
    ALUNO("Aluno");

    override fun toString(): String {
        return descricao
    }
}