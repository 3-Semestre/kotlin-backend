package grupo9.eduinovatte.application.dto.request

import grupo9.eduinovatte.model.enums.NichoNome
import grupo9.eduinovatte.model.enums.NivelInglesNome

data class FiltroForm(
    val nome: String?,
    val cpf: String?,
    val nicho: NichoNome?,
    val nivelIngles: NivelInglesNome?
)