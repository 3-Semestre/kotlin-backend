package grupo9.eduinovatte.service

import grupo9.eduinovatte.model.Usuario
import grupo9.eduinovatte.model.enums.NivelAcessoNome
import org.springframework.data.jpa.repository.JpaRepository

interface UsuarioRepository: JpaRepository<Usuario, Int> {
    fun findByNivelAcessoNome(nome: NivelAcessoNome): List<Usuario>
}