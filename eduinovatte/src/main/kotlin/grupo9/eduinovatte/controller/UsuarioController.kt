package grupo9.eduinovatte.controller

import grupo9.eduinovatte.dto.LoginForm
import grupo9.eduinovatte.model.*
import grupo9.eduinovatte.model.enums.NivelAcessoNome
import grupo9.eduinovatte.service.NivelAcessoRepository
import grupo9.eduinovatte.service.UsuarioRepository
import jakarta.validation.Valid
import org.springframework.dao.EmptyResultDataAccessException
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
    @PostMapping("/aluno/autenticar")
    fun autenticarAluno(@RequestBody loginForm: LoginForm): ResponseEntity<Usuario>{
        try {
            val usuario = usuarioRepository.findByEmailOrCpfAndSenha(loginForm.email, loginForm.cpf, loginForm.senha)
            val nivelAcesso = buscaNivelAcesso(usuario.nivelAcesso.id)
            if(nivelAcesso.nome == NivelAcessoNome.ALUNO){
                usuarioRepository.autenticarAluno(usuario.id)
                val novoUsuario = usuarioRepository.findById(usuario.id).get()
                return ResponseEntity.status(201).body(novoUsuario.copy(autenticado = true))
            }
            return ResponseEntity.status(401).build()
        } catch (e: EmptyResultDataAccessException) {
            return ResponseEntity.status(403).build()
        }
    }

    @PostMapping("/aluno/desautenticar/{id}")
    fun desautenticarAluno(@PathVariable id: Int): ResponseEntity<Void>{
        if (usuarioRepository.existsById(id)) {
            val usuarioDesautentifcado = usuarioRepository.findById(id).get()
            val nivelAcesso = buscaNivelAcesso(usuarioDesautentifcado.nivelAcesso.id)
            if(nivelAcesso.nome !== NivelAcessoNome.ALUNO) return ResponseEntity.status(401).build()
            usuarioRepository.desautenticarAluno(id)
            return ResponseEntity.status(200).build()
        }
        return ResponseEntity.status(404).build()
    }

    @PostMapping("/professor/autenticar")
    fun autenticarProfessor(@RequestBody novoProfessor: Usuario): ResponseEntity<Usuario>{

        val nivelAcesso = buscaNivelAcesso(novoProfessor.nivelAcesso.id)
        if(nivelAcesso.nome !== NivelAcessoNome.PROFESSOR_AUXILIAR) return ResponseEntity.status(401).build()
        val usuarioSalvo = usuarioRepository.save(novoProfessor)
        return ResponseEntity.status(201).body(usuarioSalvo)
    }

    @PostMapping("/aluno")
    fun salvaAluno(@RequestBody @Valid novoAluno: Usuario): ResponseEntity<Usuario>{
        val nivelAcesso = buscaNivelAcesso(novoAluno.nivelAcesso.id)
        if(nivelAcesso.nome !== NivelAcessoNome.ALUNO) return ResponseEntity.status(401).build()
        val usuarioSalvo = usuarioRepository.save(novoAluno)
        return ResponseEntity.status(201).body(usuarioSalvo)
    }

    @PutMapping("/aluno/{id}")
    fun editaAluno(
        @PathVariable id: Int,@RequestBody novoUsuario: Usuario):
            ResponseEntity<Usuario> {
        if (usuarioRepository.existsById(id)) {
            val usuarioAntigo = usuarioRepository.findById(id).get()
            val nivelAcessoUsuarioAntigo = buscaNivelAcesso(usuarioAntigo.nivelAcesso.id)
            val nivelAcessoNovoUsuario = buscaNivelAcesso(novoUsuario.nivelAcesso.id)
            if(nivelAcessoNovoUsuario.nome !== NivelAcessoNome.ALUNO || nivelAcessoUsuarioAntigo.nome !== NivelAcessoNome.ALUNO) return ResponseEntity.status(401).build()
            novoUsuario.id = id
            usuarioRepository.save(novoUsuario)
            return ResponseEntity.status(200).body(novoUsuario)
        }
        return ResponseEntity.status(404).build()
    }

    @DeleteMapping("/aluno/{id}")
    fun deletaAluno(@PathVariable id: Int):ResponseEntity<Void> {

        if (usuarioRepository.existsById(id)) {
            val usuario = usuarioRepository.findById(id).get()
            val nivelAcesso = buscaNivelAcesso(usuario.nivelAcesso.id)
            if(nivelAcesso.nome !== NivelAcessoNome.ALUNO) return ResponseEntity.status(401).build()
            usuarioRepository.deleteById(id)
            return ResponseEntity.status(204).build()
        }
        return ResponseEntity.status(404).build()
    }

    @GetMapping("/aluno")
    fun buscaAlunos(): ResponseEntity<List<Usuario>>{
        val listaAlunos = usuarioRepository.findByNivelAcessoNome(NivelAcessoNome.ALUNO)
        if(listaAlunos.isEmpty()){
            return ResponseEntity.status(204).build()
        }
        return ResponseEntity.status(200).body(listaAlunos)
    }
    @PostMapping("/professor")
    fun salvaProfessor(@RequestBody @Valid novoProfessor: Usuario): ResponseEntity<Usuario>{

        val nivelAcesso = buscaNivelAcesso(novoProfessor.nivelAcesso.id)
        if(nivelAcesso.nome !== NivelAcessoNome.PROFESSOR_AUXILIAR) return ResponseEntity.status(401).build()
        val usuarioSalvo = usuarioRepository.save(novoProfessor)
        return ResponseEntity.status(201).body(usuarioSalvo)
    }
    @GetMapping("/professor")
    fun buscaProfessores(): ResponseEntity<List<Usuario>>{
        val listaProfessores = usuarioRepository.findByNivelAcessoNome(NivelAcessoNome.PROFESSOR_AUXILIAR)
        if(listaProfessores.isEmpty()){
            return ResponseEntity.status(204).build()
        }
        return ResponseEntity.status(200).body(listaProfessores)
    }

    @PutMapping("/professor/{id}")
    fun editaProfessor(
        @PathVariable id: Int,@RequestBody @Valid novoUsuario: Usuario):
            ResponseEntity<Usuario> {

        if (usuarioRepository.existsById(id)) {
            val usuarioAntigo = usuarioRepository.findById(id).get()
            val nivelAcessoUsuarioAntigo = buscaNivelAcesso(usuarioAntigo.nivelAcesso.id)
            val nivelAcessoNovoUsuario = buscaNivelAcesso(novoUsuario.nivelAcesso.id)
            if(nivelAcessoNovoUsuario.nome !== NivelAcessoNome.PROFESSOR_AUXILIAR || nivelAcessoUsuarioAntigo.nome !== NivelAcessoNome.PROFESSOR_AUXILIAR) return ResponseEntity.status(401).build()
            novoUsuario.id = id
            usuarioRepository.save(novoUsuario)
            return ResponseEntity.status(200).body(novoUsuario)
        }
        return ResponseEntity.status(404).build()
    }

    @DeleteMapping("/professor/{id}")
    fun deletaProfessor(@PathVariable id: Int):ResponseEntity<Void> {

        if (usuarioRepository.existsById(id)) {
            val usuario = usuarioRepository.findById(id).get()
            val nivelAcesso = buscaNivelAcesso(usuario.nivelAcesso.id)
            if(nivelAcesso.nome !== NivelAcessoNome.PROFESSOR_AUXILIAR) return ResponseEntity.status(401).build()
            usuarioRepository.deleteById(id)
            return ResponseEntity.status(204).build()
        }
        return ResponseEntity.status(404).build()
    }

    @GetMapping("/representante-legal")
    fun buscaRepresentanteLegal(): ResponseEntity<List<Usuario>>{
        val listaProfessores = usuarioRepository.findByNivelAcessoNome(NivelAcessoNome.REPRESENTANTE_LEGAL)
        if(listaProfessores.isEmpty()){
            return ResponseEntity.status(204).build()
        }
        return ResponseEntity.status(200).body(listaProfessores)
    }

    fun buscaNivelAcesso(id:Int): NivelAcesso{
        return nivelAcessoRepository.findById(id).get()
    }
}