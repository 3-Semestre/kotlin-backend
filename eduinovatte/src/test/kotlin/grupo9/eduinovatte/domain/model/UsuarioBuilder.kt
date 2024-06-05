package grupo9.eduinovatte.model

import grupo9.eduinovatte.model.enums.NivelAcessoNome
import java.time.LocalDate

class UsuarioBuilder {
    var id: Int? = 1
    var nomeCompleto: String = "Nome Completo"
    var cpf: String = "000.000.000-00"
    var telefone: String? = "00000000000"
    var autenticado: Boolean? = false
    val dataNasc: LocalDate? = LocalDate.of(2005,5,5)
    val dataCadastro: LocalDate? = LocalDate.now()
    var email: String = "email@teste.com"
    var senha: String? = "senha123"
    var profissao: String? = "Profissão"
    var nivelAcesso: NivelAcesso = NivelAcesso(id = 1, nome = NivelAcessoNome.ALUNO)

    fun withId(id: Int?) = apply { this.id = id }
    fun withNomeCompleto(nomeCompleto: String) = apply { this.nomeCompleto = nomeCompleto }
    fun withCpf(cpf: String) = apply { this.cpf = cpf }
    fun withTelefone(telefone: String?) = apply { this.telefone = telefone }
    fun withAutenticado(autenticado: Boolean?) = apply { this.autenticado = autenticado }
    fun withEmail(email: String) = apply { this.email = email }
    fun withSenha(senha: String?) = apply { this.senha = senha }
    fun withProfissao(profissao: String?) = apply { this.profissao = profissao }
    fun withNivelAcesso(nivelAcesso: NivelAcesso) = apply { this.nivelAcesso = nivelAcesso }

    fun build(): Usuario {
        return Usuario(
            id = id,
            nomeCompleto = nomeCompleto,
            cpf = cpf,
            telefone = telefone,
            autenticado = autenticado,
            email = email,
            senha = senha,
            profissao = profissao,
            nivelAcesso = nivelAcesso,
            dataNascimento = dataNasc,
            dataCadastro = dataCadastro,
        )
    }
}
