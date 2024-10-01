package grupo9.eduinovatte.service

import grupo9.eduinovatte.application.dto.response.AgendamentoProximosProjection
import grupo9.eduinovatte.domain.repository.UsuarioPerfilAlunoViewProjection
import grupo9.eduinovatte.domain.repository.UsuarioPerfilViewProjection
import grupo9.eduinovatte.model.Usuario
import grupo9.eduinovatte.model.enums.NivelAcessoNome
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface UsuarioRepository: JpaRepository<Usuario, Int> {
    fun findByNivelAcessoNome(nome: NivelAcessoNome?): List<Usuario>?

    fun findByCpf(cpf: String): Usuario

    @Query("SELECT u FROM Usuario u WHERE (u.email = :email OR u.cpf = :cpf) AND u.senha = :senha")
    fun findByEmailOrCpfAndSenha(email: String?, cpf: String?, senha: String?): Usuario
    @Transactional
    @Modifying
    @Query("update Usuario u set u.autenticado = true where u.id = :id")
    fun autenticar(id: Int?): Int

    @Transactional
    @Modifying
    @Query("update Usuario u set u.autenticado = false where u.id = :id")
    fun desautenticar(id: Int?): Int

    @Transactional
    @Modifying
    @Query("update Usuario u set u.situacao.id = 2 where u.id = :id")
    fun desativar(id: Int?): Int

    @Query(value = "SELECT * FROM perfil WHERE id = :id", nativeQuery = true)
    fun exibirPerfil(@Param("id") id: Int): UsuarioPerfilViewProjection?

    @Query(value = "SELECT * FROM perfil_aluno WHERE id = :id", nativeQuery = true)
    fun exibirPerfilAluno(@Param("id") id: Int): UsuarioPerfilAlunoViewProjection?
}