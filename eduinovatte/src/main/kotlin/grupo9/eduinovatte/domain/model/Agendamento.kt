package grupo9.eduinovatte.model

import grupo9.eduinovatte.domain.model.HistoricoAgendamento
import jakarta.persistence.*
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

    @ManyToOne
    @JoinColumn(name = "fk_professor")
    var professor: Usuario?,

    @ManyToOne
    @JoinColumn(name = "fk_aluno")
    var aluno: Usuario?,

    @OneToMany(mappedBy = "agendamento")
    var historico: List<HistoricoAgendamento>?
)
