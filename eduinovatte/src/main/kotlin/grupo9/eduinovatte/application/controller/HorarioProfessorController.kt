package grupo9.eduinovatte.application.controller

import grupo9.eduinovatte.domain.service.HorarioProfessorService
import grupo9.eduinovatte.model.HorarioProfessor
import grupo9.eduinovatte.model.Usuario
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/horario-professor")
class HorarioProfessorController (
    val horarioProfessorService: HorarioProfessorService
){

    @GetMapping
    fun buscaHorarios(): ResponseEntity<List<HorarioProfessor>>{
        val horarios = horarioProfessorService.buscarHorarios()
        if(horarios.isEmpty()) return ResponseEntity.status(204).build()
        return ResponseEntity.status(200).body(horarios)
    }

    /*
    fun adicionaHorario(professor: Usuario): ResponseEntity<List<HorarioProfessor>>{
        val horarioProfessor = HorarioProfessor()
        val horarios = horarioProfessorService.adicionaHorario()
        if(horarios.isEmpty()) return ResponseEntity.status(204).build()
        return ResponseEntity.status(200).body(horarios)
    }
     */
}