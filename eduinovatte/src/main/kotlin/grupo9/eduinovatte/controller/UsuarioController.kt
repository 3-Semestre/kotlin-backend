package grupo9.eduinovatte.controller

import grupo9.eduinovatte.model.*
import grupo9.eduinovatte.service.NivelAcessoRepository
import grupo9.eduinovatte.service.UsuarioRepository
import org.apache.coyote.Response
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
@RequestMapping("/usuarios")
class UsuarioController(
    val usuarioRepository: UsuarioRepository,
    val nivelAcessoRepository: NivelAcessoRepository
){
    @PostMapping("/alunos")
    fun salvaAluno(@RequestBody novoAluno: Usuario): ResponseEntity<Usuario>{
        val nivelAcesso = buscaPorNivelAcesso(novoAluno.nivelAcesso.id)
        if(nivelAcesso.nome !== Nivel.ALUNO) return ResponseEntity.status(401).build()
        val usuarioSalvo = usuarioRepository.save(novoAluno)
        return ResponseEntity.status(201).body(usuarioSalvo)
    }

    @PutMapping("/{id}")
    fun editaAluno(
        @PathVariable id: Int,@RequestBody novoAluno: Usuario):
            ResponseEntity<Usuario> {
        val nivelAcesso = buscaPorNivelAcesso(novoAluno.nivelAcesso.id)
        if(nivelAcesso.nome !== Nivel.ALUNO) return ResponseEntity.status(401).build()
        if (usuarioRepository.existsById(id)) {
            novoAluno.id = id
            usuarioRepository.save(novoAluno)
            return ResponseEntity.status(200).body(novoAluno)
        }
        return ResponseEntity.status(404).build()
    }

    @DeleteMapping("/{id}")
    fun deletaAluno(@PathVariable id: Int):ResponseEntity<Void> {
        val nivelAcesso = buscaPorNivelAcesso(id)
        if(nivelAcesso.nome !== Nivel.ALUNO) return ResponseEntity.status(401).build()
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id)
            return ResponseEntity.status(204).build()
        }
        return ResponseEntity.status(404).build()
    }

    @GetMapping("/professor")
    fun buscaAlunos(): ResponseEntity<List<Usuario>>{
        val listaAlunos = usuarioRepository.findByNivelAcessoId(1)
        if(listaAlunos.isEmpty()){
            return ResponseEntity.status(204).build()
        }
        return ResponseEntity.status(200).body(listaAlunos)
    }
    @PostMapping("/professor")
    fun salvaProfessor(@RequestBody novoProfessor: Usuario): ResponseEntity<Usuario>{

        val nivelAcesso = buscaPorNivelAcesso(novoProfessor.nivelAcesso.id)
        if(nivelAcesso.nome !== Nivel.PROFESSOR_AUXILIAR) return ResponseEntity.status(401).build()
        val usuarioSalvo = usuarioRepository.save(novoProfessor)
        return ResponseEntity.status(201).body(usuarioSalvo)
    }
    @GetMapping("/professor")
    fun buscaProfessores(): ResponseEntity<List<Usuario>>{
        val listaProfessores = usuarioRepository.findByNivelAcessoId(2)
        if(listaProfessores.isEmpty()){
            return ResponseEntity.status(204).build()
        }
        return ResponseEntity.status(200).body(listaProfessores)
    }

    @PutMapping("/{id}")
    fun put(
        @PathVariable id: Int,@RequestBody novoProfessor: Usuario):
            ResponseEntity<Usuario> {
        val nivelAcesso = buscaPorNivelAcesso(novoProfessor.nivelAcesso.id)
        if(nivelAcesso.nome !== Nivel.PROFESSOR_AUXILIAR) return ResponseEntity.status(401).build()
        if (usuarioRepository.existsById(id)) {
            novoProfessor.id = id
            usuarioRepository.save(novoProfessor)
            return ResponseEntity.status(200).body(novoProfessor)
        }
        return ResponseEntity.status(404).build()
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Int):ResponseEntity<Void> {
        val nivelAcesso = buscaPorNivelAcesso(id)
        if(nivelAcesso.nome !== Nivel.PROFESSOR_AUXILIAR) return ResponseEntity.status(401).build()
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id)
            return ResponseEntity.status(204).build()
        }
        return ResponseEntity.status(404).build()
    }

    fun buscaPorNivelAcesso(id:Int): NivelAcesso{
        return nivelAcessoRepository.findById(id).get()
    }
}