package grupo9.eduinovatte.model

data class Professor (
    val nomeCompleto: String,
    val email: String,
    val senha: String,
    val endereco: Endereco,
    val experiencia: Experiencia
){
}