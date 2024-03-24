package grupo9.eduinovatte.model

data class Imagem (
    val nome: String,
    // dados da imagem codificados em Base64
    val dados: ByteArray,
    // Tamanho do arquivo em bytes
    val tamanho: Long
){

}
