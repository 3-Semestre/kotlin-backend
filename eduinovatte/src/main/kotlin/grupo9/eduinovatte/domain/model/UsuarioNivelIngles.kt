package grupo9.eduinovatte.domain.model

import grupo9.eduinovatte.model.NivelIngles
import grupo9.eduinovatte.model.Usuario
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne

@Entity
class UsuarioNivelIngles (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int,

    @ManyToOne
    val usuario: Usuario?,

    @ManyToOne
    val nivelIngles: NivelIngles
)