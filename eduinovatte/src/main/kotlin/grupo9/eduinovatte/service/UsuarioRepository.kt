package grupo9.eduinovatte.service

import grupo9.eduinovatte.model.Usuario
import grupo9.eduinovatte.model.enums.NivelAcessoNome
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query

interface UsuarioRepository: JpaRepository<Usuario, Int> {
    fun findByNivelAcessoNome(nome: NivelAcessoNome): List<Usuario>

    fun findByEmailOrCpfAndSenha(email: String?, cpf: String?, senha: String?): Usuario
    @Transactional
    @Modifying
    @Query("update Usuario u set u.autenticado = true where u.id = :id")
    fun autenticarAluno(id: Int): Int

    @Transactional
    @Modifying
    @Query("update Usuario u set u.autenticado = false where u.id = :id")
    fun desautenticarAluno(id: Int): Int
}