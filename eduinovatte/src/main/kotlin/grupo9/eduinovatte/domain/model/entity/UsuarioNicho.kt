package grupo9.eduinovatte.domain.model.entity

import jakarta.persistence.*
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction

@Entity
data class UsuarioNicho (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int,

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne
    val usuario: Usuario,

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne
    val nicho: Nicho
)