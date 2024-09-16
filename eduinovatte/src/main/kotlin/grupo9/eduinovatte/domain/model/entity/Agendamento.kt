package grupo9.eduinovatte.domain.model.entity

import jakarta.persistence.*
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import java.time.LocalDate
import java.time.LocalTime

@Entity
data class Agendamento (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    var data: LocalDate?,

    var horarioInicio: LocalTime?,

    var horarioFim: LocalTime?,

    var assunto: String,

    @ManyToOne
    @JoinColumn(name = "fk_professor")
    @OnDelete(action = OnDeleteAction.CASCADE)
    var professor: Usuario?,

    @ManyToOne
    @JoinColumn(name = "fk_aluno")
    @OnDelete(action = OnDeleteAction.CASCADE)
    var aluno: Usuario?,

    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToMany(mappedBy = "agendamento", cascade = [CascadeType.ALL], orphanRemoval = true)
    var historico: List<Andamento>?
)
