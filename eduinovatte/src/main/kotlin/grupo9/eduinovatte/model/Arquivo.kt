package grupo9.eduinovatte.model

data class Arquivo (
    val nome: String?,
    // Tamanho do arquivo em bytes
    val tamanho: Long,
    val tipo: String?,
    val professor: Int,
    // dados da imagem codificados em Base64
    val dados: ByteArray,
){

}
