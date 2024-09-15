package grupo9.eduinovatte.controller

import grupo9.eduinovatte.application.dto.request.AgendamentoCadastro
import grupo9.eduinovatte.application.dto.request.FiltroAgendamentoForm
import grupo9.eduinovatte.application.dto.response.AgendamentoListagemResponse
import grupo9.eduinovatte.domain.service.AgendamentoService
import grupo9.eduinovatte.domain.model.entity.Agendamento
import grupo9.eduinovatte.domain.model.entity.Usuario
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
    fun retornaAgendamentos(historico: List<Agendamento?>): List<AgendamentoListagemResponse> {

        val dto = historico.map {
            mapper.map(it, AgendamentoListagemResponse::class.java)
        }

        return dto
    }

    @Operation(summary = "Busque todos os agendamentos", description = "Busque todos os agendamentos de um aluno.")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Agendamentos encontrados"),
        ApiResponse(responseCode = "204", description = "Nenhum agendamento encontrado")
    ])
    @CrossOrigin
    @GetMapping("/{tipo}/{id}")
    fun buscaHistoricoAgendamentoPorUsuario(@PathVariable tipo: Int ,@PathVariable id: Int): ResponseEntity<List<AgendamentoListagemResponse>> {
        val listaDeHistorico = agendamentoService.buscaAgendamentosUsuario(tipo, id)

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

    @CrossOrigin
    @PostMapping("/filtro/{tipo}/{id}")
    fun filtroAgendamento(@PathVariable tipo: String, @RequestBody filtro: FiltroAgendamentoForm, @PathVariable id: Int): ResponseEntity<List<AgendamentoListagemResponse>>{
        val lista = when (tipo) {
            "aluno" -> agendamentoService.filtrarAluno(filtro, id)
            "professor" -> agendamentoService.filtrarProfessor(filtro, id)
            "professor_auxiliar" -> agendamentoService.filtrarProfessor(filtro, id)
            else -> return ResponseEntity.status(401).build()
        }

        var listaAgendamentos = retornaAgendamentos(lista)

        return ResponseEntity.status(200).body(listaAgendamentos)
    }

}