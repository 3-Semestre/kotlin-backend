package grupo9.eduinovatte.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import grupo9.eduinovatte.domain.repository.projection.UsuarioPerfilAlunoViewProjection
import grupo9.eduinovatte.domain.repository.projection.UsuarioPerfilViewProjection
import grupo9.eduinovatte.domain.model.entity.Usuario
import grupo9.eduinovatte.model.enums.NivelAcessoNome
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface UsuarioRepository : JpaRepository<Usuario, Int> {
    fun findByNivelAcessoNome(nome: NivelAcessoNome?): List<Usuario>?

    fun findByCpf(cpf: String): Optional<Usuario>

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
    @Query("UPDATE Usuario u SET u.situacao.id = :status WHERE u.id = :id")
    fun atualizarStatusUsuario(@Param("id") id: Int, @Param("status") status: Int): Int

    @Query(value = "SELECT * FROM perfil_professor WHERE id = :id", nativeQuery = true)
    fun exibirPerfil(@Param("id") id: Int): UsuarioPerfilViewProjection?

    @Query(value = "SELECT * FROM perfil WHERE id = :id", nativeQuery = true)
    fun exibirPerfilAluno(@Param("id") id: Int): UsuarioPerfilAlunoViewProjection?

    @Query(
        value = """
        SELECT * 
        FROM perfil_professor
        WHERE (:nomeCompleto IS NULL OR LOWER(nome_completo) LIKE LOWER(CONCAT(:nomeCompleto, '%')))
          AND (:cpf IS NULL OR cpf = :cpf)
          AND (:nicho IS NULL OR nichos LIKE LOWER(CONCAT('%', :nicho, '%')))
          AND (:nivel IS NULL OR niveis_ingles LIKE LOWER(CONCAT('%', :nivel, '%')))
          AND (:situacao IS NULL OR status = :situacao)
    """,
        nativeQuery = true
    )
    fun exibirProfessores(
        pageable: Pageable,
        @Param("nomeCompleto") nome: String?,
        @Param("cpf") cpf: String?,
        @Param("nicho") nicho: String?,
        @Param("nivel") nivelIngles: String?,
        @Param("situacao") situacao: String?
    ): Page<UsuarioPerfilViewProjection>

    @Query(
        value = """
        SELECT * 
        FROM perfil 
        WHERE (:nomeCompleto IS NULL OR LOWER(nome_completo) LIKE LOWER(CONCAT(:nomeCompleto, '%')))
          AND (:cpf IS NULL OR cpf = :cpf)
          AND (:nicho IS NULL OR nichos LIKE LOWER(CONCAT('%', :nicho, '%')))
          AND (:nivel IS NULL OR niveis_ingles LIKE LOWER(CONCAT('%', :nivel, '%')))
          AND (:situacao IS NULL OR status = :situacao)
    """,
        nativeQuery = true
    )
    fun exibirAlunos(
        pageable: Pageable,
        @Param("nomeCompleto") nome: String?,
        @Param("cpf") cpf: String?,
        @Param("nicho") nicho: String?,
        @Param("nivel") nivelIngles: String?,
        @Param("situacao") situacao: String?
    ): Page<UsuarioPerfilAlunoViewProjection>
}