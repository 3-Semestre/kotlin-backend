package grupo9.eduinovatte.controller

import grupo9.eduinovatte.model.Professor
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/professor")
class ProfessorController {
    var ListaProfessores: MutableList<Professor> = mutableListOf()

    @GetMapping
    fun buscaProfessores(): ResponseEntity<List<Professor>>{
        if(ListaProfessores.isEmpty()){
            return ResponseEntity.status(204).build()
        }
        return ResponseEntity.status(200).body(ListaProfessores)
    }

    @PostMapping
    fun adicionaProfessor(@RequestBody professor: Professor): ResponseEntity<Professor>{
        ListaProfessores.add(professor)
        return ResponseEntity.status(201).body(professor)
    }

    @PutMapping("/{id}")
    fun editaProfessor(@RequestBody professorEditado: Professor,@PathVariable id: Int): ResponseEntity<Professor>{
        if(existeProfessor(id)){
            ListaProfessores.set(id, professorEditado)
            return ResponseEntity.status(200).body(professorEditado)
        }
        return ResponseEntity.status(404).build()
    }

    @DeleteMapping("/{id}")
    fun deletaProfessor(@PathVariable id: Int): ResponseEntity<Void> {
        if(existeProfessor(id)){
            ListaProfessores.removeAt(id)
        }
        return ResponseEntity.status(404).build()
    }

    fun existeProfessor(id: Int): Boolean{
        return id >= 0 && id <= ListaProfessores.size
    }
}