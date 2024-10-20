package grupo9.eduinovatte.domain.model.entity

import jakarta.persistence.*
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction

@Entity
data class Meta (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,

    @Column(name = "qtd_aula")
    val qtdAula: Int,

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne
    val usuario: Usuario,
)