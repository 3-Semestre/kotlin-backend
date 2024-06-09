package grupo9.eduinovatte.controller

import grupo9.eduinovatte.application.dto.request.AgendamentoCadastro
import grupo9.eduinovatte.application.dto.response.AgendamentoListagemResponse
import grupo9.eduinovatte.application.dto.response.UsuarioNomeSemDetalhesResponse
import grupo9.eduinovatte.application.dto.response.UsuarioResponse
import grupo9.eduinovatte.domain.service.AgendamentoService
import grupo9.eduinovatte.model.Agendamento
import grupo9.eduinovatte.model.Usuario
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.modelmapper.ModelMapper
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/agendamento")
class AgendamentoController (
    val agendamentoService: AgendamentoService,
    val mapper: ModelMapper = ModelMapper()
){
    fun retornaAgendamentos(historico: List<Agendamento>): List<AgendamentoListagemResponse> {

        val dto = historico.map {
            mapper.map(it, AgendamentoListagemResponse::class.java)
        }
//teste
        return dto
    }

    @Operation(summary = "Busque todos os agendamentos", description = "Busque todos os agendamentos de um usuario.")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Agendamentos encontrados"),
        ApiResponse(responseCode = "204", description = "Nenhum agendamento encontrado")
    ])
    @GetMapping
    fun buscaHistoricoAgendamentoPorUsuario(@RequestBody usuario: UsuarioNomeSemDetalhesResponse): ResponseEntity<List<AgendamentoListagemResponse>> {
        val listaDeHistorico = agendamentoService.buscaAgendamentosUsuario(usuario)

        if(listaDeHistorico.isEmpty()) {
            return ResponseEntity.status(204).build()
        }

        var listaAgendamentos = retornaAgendamentos(listaDeHistorico)

        return ResponseEntity.status(200).body(listaAgendamentos)
    }

    @Operation(summary = "Salve um agendamentos", description = "Salve um novo agendamentos no sistema.")
    @ApiResponses(value = [
        ApiResponse(responseCode = "201", description = "Criado com sucesso"),
        ApiResponse(responseCode = "401", description = "Erro no nível de acesso no corpo da requisição")
    ])
    @PostMapping
    fun salvaAgendamento(@RequestBody novoAgendamento: Agendamento): ResponseEntity<AgendamentoCadastro> {
        val agendamentoSalvo = agendamentoService.salvarAgendamento(novoAgendamento)

        return ResponseEntity.status(201).body(agendamentoSalvo)
    }

}