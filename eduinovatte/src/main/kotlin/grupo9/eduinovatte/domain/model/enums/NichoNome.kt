package grupo9.eduinovatte.model.enums

enum class NichoNome(val descricao: String){
    INFANTIL("Inglês para crianças de 10 a 20 anos"),
    BUSINESS("Inglês com contexto corporativo"),
    TECNICO("Inglês com contexto técnico (ex: aviação, jurídico, médico)"),
    TESTES_INTERNACIONAIS("Inglês para exames internacionais"),
    MORADORES_EXTERIOR("Inglês para estrangeiros");

    override fun toString(): String {
        return descricao
    }
}