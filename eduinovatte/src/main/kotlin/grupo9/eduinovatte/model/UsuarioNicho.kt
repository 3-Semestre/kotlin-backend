package grupo9.eduinovatte.model

import jakarta.persistence.*

@Entity
data class UsuarioNicho (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int,
    @field:ManyToOne
    val usuario: Usuario,
    @field:ManyToOne
    val nicho: Nicho,
    @field:ManyToOne
    val nivelIngles: NivelIngles
)