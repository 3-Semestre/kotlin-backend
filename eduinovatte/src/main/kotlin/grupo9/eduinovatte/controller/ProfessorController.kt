package grupo9.eduinovatte.controller

import grupo9.eduinovatte.model.*
import jakarta.websocket.server.PathParam
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import javax.swing.plaf.multi.MultiTableUI
import kotlin.reflect.typeOf


@RestController
@RequestMapping("/professor")
class ProfessorController {

     var listaProfessores: MutableList<Professor> = mutableListOf(Professor(
        nomeCompleto = "Cristian",
        email = "cristian@email.com",
        senha = "banana123",
        cpf = "38274627225",
        nivelAcesso = NivelAcesso.REPRESENTANTE_LEGAL,
        nivelIngles = NivelIngles.B2
    ))

    @GetMapping
    fun buscaProfessores(): ResponseEntity<List<Professor>>{
        if(listaProfessores.isEmpty()){
            return ResponseEntity.status(204).build()
        }
        return ResponseEntity.status(200).body(listaProfessores)
    }

    @GetMapping("/{indice}")
    fun buscaProfessor(@PathVariable indice: Int): ResponseEntity<Professor>{
        val professorComId = buscaProfessorPorId(indice)
        if(professorComId != null){
            return ResponseEntity.status(200).body(listaProfessores[indice - 1])
        }
        return ResponseEntity.status(404).build()
    }

    @PostMapping
    fun adicionaProfessor(@RequestBody professor: Professor): ResponseEntity<Professor>{
        if(existeProfessorComCPF(professor)){
            return ResponseEntity.status(409).build()
        }
        listaProfessores.add(professor)
        return ResponseEntity.status(201).body(professor)
    }

    @PutMapping("/{id}")
    fun editaProfessor(@RequestBody professorEditado: Professor,@PathVariable id: Int): ResponseEntity<Professor>{
        if(existeProfessorComId(id)){
            listaProfessores.set(id - 1, professorEditado)
            return ResponseEntity.status(200).body(professorEditado)
        }
        return ResponseEntity.status(404).build()
    }

    @DeleteMapping("/{indice}")
    fun deletaProfessor(@PathVariable indice: Int): ResponseEntity<Void> {
        if(existeProfessorComId(indice)){
            listaProfessores.removeAt(indice - 1)
            return ResponseEntity.status(204).build()
        }
        return ResponseEntity.status(404).build()
    }

    fun existeProfessorComId(indice: Int): Boolean{
        return indice > 0 && indice <= listaProfessores.size
    }

    fun buscaProfessorPorId(indice: Int): Professor? {
        if(indice > 0 && indice <= listaProfessores.size) {
            return listaProfessores[indice - 1]
        }
        return null
    }

    private fun existeProfessorComCPF(professor: Professor): Boolean {
        listaProfessores.forEach{
            if(it.cpf == professor.cpf){
                return true
            }
        }
        return false
    }
}