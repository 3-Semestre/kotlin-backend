package grupo9.eduinovatte.application.dto.request

import jakarta.validation.constraints.Email

data class LoginForm(
    val email: String?,
    val cpf: String?,
    val senha: String
)