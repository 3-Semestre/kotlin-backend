package grupo9.eduinovatte.service

import grupo9.eduinovatte.application.dto.request.FiltroForm
import grupo9.eduinovatte.domain.model.entity.NivelAcesso
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
    fun findByNivelAcessoNome(nome: NivelAcessoNome?): List<Usuario>

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

    @Query("""
    SELECT u FROM Usuario u
    LEFT JOIN UsuarioNicho un ON u.id = un.usuario.id
    LEFT JOIN Nicho n ON un.nicho.id = n.id
    LEFT JOIN UsuarioNivelIngles uni ON u.id = uni.usuario.id
    LEFT JOIN NivelIngles ni ON uni.nivelIngles.id = ni.id
    WHERE (:nome IS NULL OR u.nomeCompleto LIKE %:nome%)
      AND (:cpf IS NULL OR u.cpf = :cpf)
      AND (:nicho IS NULL OR n.nome = :nicho)
      AND (:nivelIngles IS NULL OR ni.nome = :nivelIngles)
      AND (
          (:nivelAcesso <> 2 AND u.nivelAcesso.id = :nivelAcesso)
          OR (:nivelAcesso = 2 AND u.nivelAcesso.id IN (2, 3))
      )
""")
    fun filtrarUsuario(nome: String?, cpf: String?, nicho: NichoNome?, nivelIngles: NivelInglesNome?, nivelAcesso: Int): List<Usuario>?
}