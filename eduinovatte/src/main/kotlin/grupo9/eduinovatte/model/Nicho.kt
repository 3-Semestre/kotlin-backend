package grupo9.eduinovatte.model

enum class Nicho(val descricao: String){
    Infantil("Inglês para crianças de 10 a 20 anos"),
    Business("Inglês com contexto corporativo"),
    Tecnico("Inglês com contexto técnico (ex: aviação, jurídico, médico)"),
    TestesInternacionais("Inglês para exames internacionais"),
    Iniciante("Inglês para iniciantes"),
    Intermediario("Inglês para intermediarios"),
    Avancado("Inglês para avançados");

    override fun toString(): String {
        return descricao
    }
}
