package grupo9.eduinovatte.controller

import grupo9.eduinovatte.application.dto.request.HorarioTransferenciaRequest
import grupo9.eduinovatte.application.dto.requestg.HorarioProfessorRequest
import grupo9.eduinovatte.domain.model.entity.HorarioProfessor
import grupo9.eduinovatte.domain.model.entity.UsuarioNicho
import grupo9.eduinovatte.domain.repository.projection.HorarioDisponiveisProjection
import grupo9.eduinovatte.domain.repository.projection.ProfessorDisponivelTransferencia
import grupo9.eduinovatte.domain.service.HorarioProfessorService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDate


@RestController
@RequestMapping("/horario-professor")
class HorarioProfessorController(
    val horarioProfessorService: HorarioProfessorService
) {

    @Operation(summary = "Salve um usuario nicho", description = "Salve um usuario nicho com as informações dele.")
    @ApiResponses(value = [
        ApiResponse(responseCode = "201", description = "Criado com sucesso"),
        ApiResponse(responseCode = "500", description = "Usuario inválido")
    ])
    @PostMapping()
    fun salvaUsuarioNicho(
        @RequestBody @Valid novoHorarioProfessor: HorarioProfessor
    ): ResponseEntity<HorarioProfessor>{
        val horarioProfessor = horarioProfessorService.salvar(novoHorarioProfessor)

        return ResponseEntity.status(201).body(horarioProfessor)
    }

    @Operation(summary = "Busque o horario do professor", description = "Busque todos os usuarios com filtro nicho dos professores e alunos.")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Usuarios encontrados encontrados"),
        ApiResponse(responseCode = "204", description = "Nenhum Usuarios encontrado")
    ])
    @GetMapping("/{id}")
    @CrossOrigin
    fun buscarPorHorarioProfessor(@PathVariable id: Int): ResponseEntity<HorarioProfessor> {
        val usuarioNichos = horarioProfessorService.buscaPorUsuario(id)

        return ResponseEntity.status(200).body(usuarioNichos)
    }

    @Operation(summary = "Busque os usuario com o filtro", description = "Busque todos os usuarios com filtro nicho dos professores e alunos.")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Usuarios encontrados encontrados"),
        ApiResponse(responseCode = "204", description = "Nenhum Usuarios encontrado")
    ])
    @PutMapping()
    fun editaHorarioProfessor(@RequestBody @Valid novoHorarioProfessor: HorarioProfessor): ResponseEntity<HorarioProfessor> {
        val horarioProfessorEditado = horarioProfessorService.edita(novoHorarioProfessor)

        return ResponseEntity.status(200).body(horarioProfessorEditado)
    }

    @PutMapping("/{id}")
    fun editaHorarioProfessor(@PathVariable id: Int, @RequestBody @Valid novoHorarioProfessor: HorarioProfessorRequest): ResponseEntity<HorarioProfessor> {
        val horarioProfessorEditado = horarioProfessorService.edita(novoHorarioProfessor, id)

        return ResponseEntity.status(200).body(horarioProfessorEditado)
    }

    @Operation(summary = "Busque os usuario com o filtro", description = "Busque todos os usuarios com filtro nicho dos professores e alunos.")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Usuarios encontrados encontrados"),
        ApiResponse(responseCode = "204", description = "Nenhum Usuarios encontrado")
    ])
    @GetMapping()
    fun buscaHorariosProfessor(): ResponseEntity<List<HorarioProfessor>> {

        val horarios = horarioProfessorService.buscaHorarios()

        return ResponseEntity.status(200).body(horarios)
    }

    @GetMapping("disponiveis/{id}")
    fun buscaHorariosProfessorDisponiveis(@RequestParam dia: LocalDate, @PathVariable id: Int): ResponseEntity<List<HorarioDisponiveisProjection>> {

        val horarios = horarioProfessorService.buscaHorariosDisponiveis(dia, id);

        return ResponseEntity.status(200).body(horarios)
    }

    @Operation(summary = "Busque os usuario com o filtro", description = "Busque todos os usuarios com filtro nicho dos professores e alunos.")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Usuarios encontrados encontrados"),
        ApiResponse(responseCode = "204", description = "Nenhum Usuarios encontrado")
    ])
    @DeleteMapping("/{id}")
    fun deleta(@PathVariable id: Int): ResponseEntity<List<UsuarioNicho>> {

        val usuarios = horarioProfessorService.deleta(id)
        return ResponseEntity.status(204).build()
    }

    @PostMapping("/transferencia")
    fun buscaHorariosProfessorTransferencia(@RequestBody @Valid agendamento: HorarioTransferenciaRequest): ResponseEntity<List<ProfessorDisponivelTransferencia>> {

        val horarios = horarioProfessorService.buscaProfessoresTransferencia(agendamento)

        return ResponseEntity.status(200).body(horarios)
    }

}

