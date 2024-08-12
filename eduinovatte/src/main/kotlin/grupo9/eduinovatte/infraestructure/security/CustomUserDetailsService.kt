package grupo9.eduinovatte.infraestructure.security

import grupo9.eduinovatte.model.Usuario
import grupo9.eduinovatte.service.UsuarioRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component

@Component
class CustomUserDetailsService : UserDetailsService {
    @Autowired
    private val repository: UsuarioRepository? = null

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(cpf: String): UserDetails {
        val user: Usuario = repository!!.findByCpf(cpf)
        return User(user.cpf, user.senha, ArrayList())
    }
}