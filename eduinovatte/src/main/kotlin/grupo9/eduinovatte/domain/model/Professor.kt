package grupo9.eduinovatte.domain.model

import grupo9.eduinovatte.domain.model.entity.NivelAcesso
import grupo9.eduinovatte.domain.model.entity.Situacao
import grupo9.eduinovatte.model.enums.SituacaoNome
import java.time.LocalDate
import java.time.LocalTime


class Professor (
    id:Int? = null,
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
): Usuario(id, nomeCompleto, cpf, telefone, dataNascimento, dataCadastro, autenticado, email, senha, profissao, nivelAcesso)
