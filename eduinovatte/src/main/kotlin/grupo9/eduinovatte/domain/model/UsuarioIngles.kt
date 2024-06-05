package grupo9.eduinovatte.model

import jakarta.persistence.*

@Entity
data class UsuarioIngles (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int,

    @ManyToOne
    val usuario: Usuario,

    @ManyToOne
    val nivelIngles: NivelIngles,

    )