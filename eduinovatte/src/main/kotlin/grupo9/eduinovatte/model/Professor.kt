package grupo9.eduinovatte.model

import org.springframework.web.multipart.MultipartFile

data class Professor (
    val nomeCompleto: String,
    val email: String,
    val senha: String,
    val cpf: String,
    val nivelAcesso: NivelAcesso,
    val nivelIngles: NivelIngles,
   // val imagem: ByteArray
)