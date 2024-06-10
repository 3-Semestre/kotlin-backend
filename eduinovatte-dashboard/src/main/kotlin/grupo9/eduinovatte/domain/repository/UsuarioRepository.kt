package grupo9.eduinovatte.service

import grupo9.eduinovatte.model.Usuario
import grupo9.eduinovatte.model.enums.NivelAcessoNome
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.LocalDate

interface UsuarioRepository: JpaRepository<Usuario, Int> {
    @Query("SELECT u FROM Usuario u WHERE u.nivelAcesso.nome = :nome AND u.dataCadastro >= :dataCorte")
    fun findByNivelAcessoNomeAndDataCadastroDate(
        @Param("nome") nome: NivelAcessoNome,
        @Param("dataCorte") dataCorte: LocalDate
    ): List<Usuario>

    fun findByNivelAcessoNome(
        @Param("nome") nome: NivelAcessoNome
    ): List<Usuario>

    fun findByCpf(cpf: String): Usuario?

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
    @Query(value = "SELECT * FROM todos_professores;", nativeQuery = true)
    fun todosProfessores(): List<Usuario>
    @Query(value = "SELECT * FROM todos_alunos;", nativeQuery = true)
    fun todosAlunos(): List<Usuario>

}