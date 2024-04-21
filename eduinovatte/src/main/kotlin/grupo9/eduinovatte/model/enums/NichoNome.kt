package grupo9.eduinovatte.model.enums

enum class NichoNome(val descricao: String){
    INFANTIL("Inglês para crianças de 10 a 20 anos"),
    BUSINESS("Inglês com contexto corporativo"),
    TECNICO("Inglês com contexto técnico (ex: aviação, jurídico, médico)"),
    TESTES_INTERNACIONAIS("Inglês para exames internacionais"),
    INICIANTE("Inglês para iniciantes"),
    INTERMEDIARIO("Inglês para intermediarios"),
    AVANCADO("Inglês para avançados");

    override fun toString(): String {
        return descricao
    }
}