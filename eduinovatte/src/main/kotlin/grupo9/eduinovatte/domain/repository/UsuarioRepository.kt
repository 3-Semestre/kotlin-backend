package grupo9.eduinovatte.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import grupo9.eduinovatte.domain.repository.UsuarioPerfilAlunoViewProjection
import grupo9.eduinovatte.domain.repository.UsuarioPerfilViewProjection
import grupo9.eduinovatte.domain.model.entity.Usuario
import grupo9.eduinovatte.model.enums.NichoNome
import grupo9.eduinovatte.model.enums.NivelAcessoNome
import grupo9.eduinovatte.model.enums.NivelInglesNome
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface UsuarioRepository: JpaRepository<Usuario, Int> {
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
    @Query("update Usuario u set u.situacao.id = 2 where u.id = :id")
    fun desativar(id: Int?): Int

    @Query(value = "SELECT * FROM perfil_professor WHERE id = :id", nativeQuery = true)
    fun exibirPerfil(@Param("id") id: Int): UsuarioPerfilViewProjection?

    @Query(value = "SELECT * FROM perfil WHERE id = :id", nativeQuery = true)
    fun exibirPerfilAluno(@Param("id") id: Int): UsuarioPerfilAlunoViewProjection?

    @Query(value = "SELECT * FROM perfil_professor", nativeQuery = true)
    fun exibirProfessores(pageable: Pageable): Page<UsuarioPerfilViewProjection?>

    @Query(value = "SELECT * FROM perfil", nativeQuery = true)
    fun exibirAlunos(pageable: Pageable): Page<UsuarioPerfilAlunoViewProjection?>

    @Query("""
    SELECT * FROM perfil
    WHERE (:nome IS NULL OR nome_completo LIKE %:nome%)
      AND (:cpf IS NULL OR cpf = :cpf)
      AND (:nicho IS NULL OR nichos LIKE %:nicho%)
      AND (:nivelIngles IS NULL OR niveis_ingles LIKE %:nivelIngles%)
      AND (:nivelAcesso IS NULL OR nivel_acesso_id = :nivelAcesso)
""", nativeQuery = true )
    fun filtrarAluno(pageable: Pageable, nome: String?, cpf: String?, nicho: String?, nivelIngles: String?, nivelAcesso: Int): Page<UsuarioPerfilViewProjection>?

    @Query("""    
    SELECT * FROM perfil_professor
    WHERE (:nome IS NULL OR nome_completo LIKE CONCAT('%', :nome, '%'))
      AND (:cpf IS NULL OR cpf = :cpf)
      AND (:nicho IS NULL OR nichos LIKE CONCAT('%', :nicho, '%'))
      AND (:nivelIngles IS NULL OR niveis_ingles LIKE CONCAT('%', :nivelIngles, '%'))
      AND (:nivelAcesso IS NULL OR nivel_acesso_id IN (:nivelAcesso, 3))
""", nativeQuery = true)
    fun filtrarProfessor(pageable: Pageable, nome: String?, cpf: String?, nicho: String?, nivelIngles: String?, nivelAcesso: Int): Page<UsuarioPerfilViewProjection>?
}