package grupo9.eduinovatte.domain.model

import grupo9.eduinovatte.domain.model.entity.NivelAcesso
import grupo9.eduinovatte.domain.model.entity.Situacao
import grupo9.eduinovatte.domain.model.entity.Usuario
import grupo9.eduinovatte.model.enums.SituacaoNome
import java.time.LocalDate
import java.time.LocalTime


class Professor (
    nomeCompleto: String?,
    cpf: String?,
    telefone: String?,
    dataNascimento: LocalDate?,
    dataCadastro: LocalDate? = LocalDate.now(),
    autenticado: Boolean? = false,
    email: String?,
    senha: String?,
    profissao :String?,
    nivelAcesso: NivelAcesso?,
    situacao: Situacao? = Situacao(id = 1, SituacaoNome.ATIVO),
    var inicio: LocalTime = LocalTime.of(9, 0, 0),
    var fim: LocalTime = LocalTime.of(18, 0, 0),
    var pausaInicio: LocalTime = LocalTime.of(12, 0, 0),
    var pausaFim: LocalTime = LocalTime.of(13, 0, 0)
): AbstractUsuario(nomeCompleto, cpf, telefone, dataNascimento, dataCadastro, autenticado, email, senha, profissao, nivelAcesso)
{
    companion object{
        fun fromAbstractUsuario(usuario: AbstractUsuario): Usuario {
            return Usuario(
                id = null, // id é gerado automaticamente
                nomeCompleto = usuario.nomeCompleto,
                cpf = usuario.cpf,
                telefone = usuario.telefone,
                dataNascimento = usuario.dataNascimento,
                email = usuario.email,
                senha = usuario.senha,
                profissao = usuario.profissao,
                nivelAcesso = usuario.nivelAcesso,
                situacao = usuario.situacao
            )
        }

        fun returnProfessor(usuario: Usuario): Professor{
            return Professor(
                nomeCompleto = usuario.nomeCompleto,
                cpf = usuario.cpf,
                telefone = usuario.telefone,
                dataNascimento = usuario.dataNascimento,
                email = usuario.email,
                senha = usuario.senha,
                profissao = usuario.profissao,
                nivelAcesso = usuario.nivelAcesso,
                situacao = usuario.situacao
            )
        }
    }
}