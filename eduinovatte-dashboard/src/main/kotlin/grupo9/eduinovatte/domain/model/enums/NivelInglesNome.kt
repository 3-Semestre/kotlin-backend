package grupo9.eduinovatte.model.enums

enum class NivelInglesNome(val descricao: String){
    A1("Iniciante"),
    A2("Elementar"),
    B1("Intermediário"),
    B2("Intermediário Superior"),
    C1("Avançado"),
    C2("Proficiente");

    override fun toString(): String {
        return descricao
    }
}