package grupo9.eduinovatte.domain.model

import grupo9.eduinovatte.domain.model.entity.NivelAcesso
import grupo9.eduinovatte.domain.model.entity.Situacao
import grupo9.eduinovatte.model.enums.SituacaoNome
import java.time.LocalDate


abstract class Usuario (
    var id:Int? = null,
    val nomeCompleto: String?,
    val cpf: String?,
    val telefone: String?,
    var dataNascimento: LocalDate?,
    val dataCadastro: LocalDate? = LocalDate.now(),
    val autenticado: Boolean? = false,
    val email: String?,
    val senha: String?,
    val profissao :String?,
    val nivelAcesso: NivelAcesso?,
    val situacao: Situacao? = Situacao(id = 1, SituacaoNome.ATIVO)
)
