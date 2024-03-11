package grupo9.eduinovatte.model

data class Endereco (
    val logradouro: String,
    val complemento: Int?,
    val bairro: String,
    val localidade: String,
    val uf: String
){
}