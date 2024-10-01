package grupo9.eduinovatte.domain.model

import grupo9.eduinovatte.domain.model.entity.NivelAcesso
import grupo9.eduinovatte.domain.model.entity.Situacao
import grupo9.eduinovatte.model.enums.SituacaoNome
import java.time.LocalDate


class Aluno (
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
    situacao: Situacao? = Situacao(id = 1, SituacaoNome.ATIVO)
): AbstractUsuario(nomeCompleto, cpf, telefone, dataNascimento, dataCadastro, autenticado, email, senha, profissao, nivelAcesso)
