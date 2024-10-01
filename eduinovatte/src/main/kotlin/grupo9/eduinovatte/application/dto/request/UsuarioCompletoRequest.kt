package grupo9.eduinovatte.application.dto.request

import com.fasterxml.jackson.annotation.JsonFormat
import grupo9.eduinovatte.domain.model.entity.*
import grupo9.eduinovatte.model.enums.SituacaoNome
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Past
import jakarta.validation.constraints.Size
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import org.hibernate.validator.constraints.br.CPF
import java.time.LocalDate

class UsuarioCompletoRequest(
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    @field:NotNull
    val nomeCompleto: String?,

    @field:NotNull
    @field:CPF
    val cpf: String?,

    @field:Size(min = 11)
    @field:NotNull
    val telefone: String?,

    @field:Past
    @field:NotNull
    @field:JsonFormat(pattern = "yyyy-MM-dd")
    var dataNascimento: LocalDate?,

    val dataCadastro: LocalDate? = LocalDate.now(),

    val autenticado: Boolean? = false,

    @field:Email
    @field:NotNull
    val email: String?,

    val senha: String?,

    val profissao: String?,

    @OnDelete(action = OnDeleteAction.CASCADE)
    @field:ManyToOne
    val nivelAcesso: NivelAcesso?,

    @OnDelete(action = OnDeleteAction.CASCADE)
    @field:ManyToOne
    val situacao: Situacao? = Situacao(id = 1, SituacaoNome.ATIVO),

    @field:NotNull
    @field:Size(min = 1)
    val listaDeNichos: List<Nicho>? = null,

    @field:NotNull
    @field:Size(min = 1)
    val listaDeNiveis: List<NivelIngles>? = null
){
    fun convertToUsuario(novoUsuario: UsuarioCompletoRequest): Usuario {
        return Usuario(
            nomeCompleto = novoUsuario.nomeCompleto,
            cpf = novoUsuario.cpf,
            telefone = novoUsuario.telefone,
            dataNascimento = novoUsuario.dataNascimento,
            email = novoUsuario.email,
            senha = novoUsuario.senha,
            profissao = novoUsuario.profissao,
            nivelAcesso = novoUsuario.nivelAcesso,
            situacao = novoUsuario.situacao
        )
    }

}