package grupo9.eduinovatte.application.dto.response

import com.fasterxml.jackson.annotation.JsonIgnore
import grupo9.eduinovatte.domain.model.enums.StatusNome
import java.time.LocalDate
import java.time.LocalTime

data class AgendamentoListagemResponse (
    var id: Int? = null,
    var data: LocalDate? = null,
    var horarioInicio: LocalTime? = null,
    var horarioFim: LocalTime? = null,
    var assunto: String? = null,
    @JsonIgnore var historico: List<AndamentoResponse>? = null,
    var professor: UsuarioNomeSemDetalhesResponse? = null,
    var aluno: UsuarioNomeSemDetalhesResponse? = null
){
    fun getStatus(): StatusNome {
        return historico!!.last()!!.status!!.nome!!
    }
}