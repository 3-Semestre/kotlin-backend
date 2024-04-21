package grupo9.eduinovatte.dto

import jakarta.validation.constraints.Email

data class LoginForm(
    val email: String?,
    val cpf: String?,
    val senha: String
)