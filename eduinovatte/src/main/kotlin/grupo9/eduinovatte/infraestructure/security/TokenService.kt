package grupo9.eduinovatte.infraestructure.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTCreationException
import com.auth0.jwt.exceptions.JWTVerificationException
import grupo9.eduinovatte.model.Usuario
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

@Service
class TokenService {

    @Value("\${api.security.token.secret}")
    private lateinit var secret: String

    fun generateToken(usuario: Usuario): String{
        try {
            val algorithm: Algorithm = Algorithm.HMAC256(secret)

            val token: String = JWT.create()
                .withIssuer("edu-inovatte")
                .withSubject(usuario.cpf)
                .withExpiresAt(this.generateExpirationDate())
                .sign(algorithm)
            return token
        } catch (e: JWTCreationException){
            throw RuntimeException("Error while authenticating");
        }
    }

    fun validateToken(token: String?): String? {
        try {
            val algorithm = Algorithm.HMAC256(secret)
            return JWT.require(algorithm)
                .withIssuer("edu-inovatte")
                .build()
                .verify(token)
                .subject
        } catch (exception: JWTVerificationException) {
            return null
        }
    }

    private fun generateExpirationDate(): Instant {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"))
    }
}