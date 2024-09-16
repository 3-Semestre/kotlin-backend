package grupo9.eduinovatte.domain.model.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction

@Entity
class UsuarioNivelIngles (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int,

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne
    val usuario: Usuario?,

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne
    val nivelIngles: NivelIngles
)