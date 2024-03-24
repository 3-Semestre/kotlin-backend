package grupo9.eduinovatte.model

data class Professor (
    val nomeCompleto: String,
    val email: String,
    val senha: String,
    val cpf: String,
    val nivelAcesso: NivelAcesso,
    val nivelIngles: NivelIngles,
    val imagem: Imagem
)