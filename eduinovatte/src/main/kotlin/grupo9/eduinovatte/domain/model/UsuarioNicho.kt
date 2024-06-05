package grupo9.eduinovatte.model

import jakarta.persistence.*

@Entity
data class UsuarioNicho (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int,

    @ManyToOne
    val usuario: Usuario,

    @ManyToOne
    val nicho: Nicho,

    @ManyToOne
    val nivelIngles: NivelIngles
)