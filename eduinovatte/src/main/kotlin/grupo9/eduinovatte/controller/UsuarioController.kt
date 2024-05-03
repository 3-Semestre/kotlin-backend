package grupo9.eduinovatte.controller

import grupo9.eduinovatte.dto.LoginForm
import grupo9.eduinovatte.model.*
import grupo9.eduinovatte.model.enums.NivelAcessoNome
import grupo9.eduinovatte.service.NivelAcessoRepository
import grupo9.eduinovatte.service.UsuarioRepository
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
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
//    @PostMapping("/aluno/autenticar")
//    fun autenticarAluno(@RequestBody loginForm: LoginForm): ResponseEntity<Usuario>{
//        try {
//            val usuario = usuarioRepository.findByEmailOrCpfAndSenha(loginForm.email, loginForm.cpf, loginForm.senha)
//            val nivelAcesso = buscaNivelAcesso(usuario.nivelAcesso.id)
//            if(nivelAcesso.nome == NivelAcessoNome.ALUNO){
//                usuarioRepository.autenticar(usuario.id)
//                val novoUsuario = usuarioRepository.findById(usuario.id).get()
//                return ResponseEntity.status(201).body(novoUsuario.copy(autenticado = true))
//            }
//            return ResponseEntity.status(401).build()
//        } catch (e: EmptyResultDataAccessException) {
//            return ResponseEntity.status(403).build()
//        }
//    }
//
//    @PostMapping("/aluno/desautenticar/{id}")
//    fun desautenticarAluno(@PathVariable id: Int): ResponseEntity<Void>{
//        if (usuarioRepository.existsById(id)) {
//            val usuarioDesautentifcado = usuarioRepository.findById(id).get()
//            val nivelAcesso = buscaNivelAcesso(usuarioDesautentifcado.nivelAcesso.id)
//            if(nivelAcesso.nome !== NivelAcessoNome.ALUNO) return ResponseEntity.status(401).build()
//            usuarioRepository.desautenticar(id)
//            return ResponseEntity.status(200).build()
//        }
//        return ResponseEntity.status(404).build()
//    }
//
//    @PostMapping("/professor/autenticar")
//    fun autenticarProfessor(@RequestBody loginForm: LoginForm): ResponseEntity<Usuario>{
//        try {
//            val usuario = usuarioRepository.findByEmailOrCpfAndSenha(loginForm.email, loginForm.cpf, loginForm.senha)
//            val nivelAcesso = buscaNivelAcesso(usuario.nivelAcesso.id)
//            if(nivelAcesso.nome == NivelAcessoNome.PROFESSOR_AUXILIAR){
//                usuarioRepository.autenticar(usuario.id)
//                val novoUsuario = usuarioRepository.findById(usuario.id).get()
//                return ResponseEntity.status(201).body(novoUsuario.copy(autenticado = true))
//            }
//            return ResponseEntity.status(401).build()
//        } catch (e: EmptyResultDataAccessException) {
//            return ResponseEntity.status(403).build()
//        }
//    }
//
//    @PostMapping("/professor/desautenticar/{id}")
//    fun desautenticarProfessor(@PathVariable id: Int): ResponseEntity<Void>{
//        if (usuarioRepository.existsById(id)) {
//            val usuarioDesautentifcado = usuarioRepository.findById(id).get()
//            val nivelAcesso = buscaNivelAcesso(usuarioDesautentifcado.nivelAcesso.id)
//            if(nivelAcesso.nome !== NivelAcessoNome.PROFESSOR_AUXILIAR) return ResponseEntity.status(401).build()
//            usuarioRepository.desautenticar(id)
//            return ResponseEntity.status(200).build()
//        }
//        return ResponseEntity.status(404).build()
//    }
//
//    @PostMapping("/representante-legal/autenticar")
//    fun autenticarRepresentante(@RequestBody loginForm: LoginForm): ResponseEntity<Usuario>{
//        try {
//            val usuario = usuarioRepository.findByEmailOrCpfAndSenha(loginForm.email, loginForm.cpf, loginForm.senha)
//            val nivelAcesso = buscaNivelAcesso(usuario.nivelAcesso.id)
//            if(nivelAcesso.nome == NivelAcessoNome.REPRESENTANTE_LEGAL){
//                usuarioRepository.autenticar(usuario.id)
//                val novoUsuario = usuarioRepository.findById(usuario.id).get()
//                return ResponseEntity.status(201).body(novoUsuario.copy(autenticado = true))
//            }
//            return ResponseEntity.status(401).build()
//        } catch (e: EmptyResultDataAccessException) {
//            return ResponseEntity.status(403).build()
//        }
//    }
//
//    @PostMapping("/representante-legal/desautenticar/{id}")
//    fun desautenticarRepresentante(@PathVariable id: Int): ResponseEntity<Void>{
//        if (usuarioRepository.existsById(id)) {
//            val usuarioDesautentifcado = usuarioRepository.findById(id).get()
//            val nivelAcesso = buscaNivelAcesso(usuarioDesautentifcado.nivelAcesso.id)
//            if(nivelAcesso.nome !== NivelAcessoNome.REPRESENTANTE_LEGAL) return ResponseEntity.status(401).build()
//            usuarioRepository.desautenticar(id)
//            return ResponseEntity.status(200).build()
//        }
//        return ResponseEntity.status(404).build()
//    }

    @Operation(summary = "Autentique o usuário", description = "Autentique o usuário com base no tipo dele (aluno, professor ou representante legal).")
    @ApiResponses(value = [
        ApiResponse(responseCode = "201", description = "Autenticação com sucesso"),
        ApiResponse(responseCode = "403", description = "Erro no login"),
        ApiResponse(responseCode = "401", description = "Erro no nível de acesso")
    ])
    @PostMapping("/{tipo}/autenticar")
    fun autenticarUsuario(
        @PathVariable tipo: String,
        @RequestBody loginForm: LoginForm
    ): ResponseEntity<Usuario>{
        try {
            val usuario = usuarioRepository.findByEmailOrCpfAndSenha(loginForm.email, loginForm.cpf, loginForm.senha)
            val nivelAcesso = buscaNivelAcesso(usuario.nivelAcesso.id)

            val tipoAcesso = when (tipo) {
                "aluno" -> NivelAcessoNome.ALUNO
                "professor" -> NivelAcessoNome.PROFESSOR_AUXILIAR
                "representante-legal" -> NivelAcessoNome.REPRESENTANTE_LEGAL
                else -> null
            }

            if(nivelAcesso.nome == tipoAcesso){
                usuarioRepository.autenticar(usuario.id)
                val novoUsuario = usuarioRepository.findById(usuario.id).get()
                return ResponseEntity.status(201).body(novoUsuario.copy(autenticado = true))
            }
            return ResponseEntity.status(401).build()
        } catch (e: EmptyResultDataAccessException) {
            return ResponseEntity.status(403).build()
        }
    }

    @Operation(summary = "Desautentique o usuário", description = "Desautentique o usuário com base no tipo dele (aluno, professor ou representante legal)")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Desautenticação feita com sucesso"),
        ApiResponse(responseCode = "404", description = "Não existe"),
        ApiResponse(responseCode = "401", description = "Erro no nível de acesso no parâmetro da requisição")
    ])
    @PostMapping("/{tipo}/desautenticar/{id}")
    fun desautenticarUsuario(
        @PathVariable tipo: String,
        @PathVariable id: Int
    ): ResponseEntity<Void>{
        if (usuarioRepository.existsById(id)) {
            val usuarioDesautentifcado = usuarioRepository.findById(id).get()
            val nivelAcesso = buscaNivelAcesso(usuarioDesautentifcado.nivelAcesso.id)

            val tipoAcesso = when (tipo) {
                "aluno" -> NivelAcessoNome.ALUNO
                "professor" -> NivelAcessoNome.PROFESSOR_AUXILIAR
                "representante-legal" -> NivelAcessoNome.REPRESENTANTE_LEGAL
                else -> null
            }

            if(nivelAcesso.nome !== tipoAcesso) return ResponseEntity.status(401).build()
            usuarioRepository.desautenticar(id)
            return ResponseEntity.status(200).build()
        }
        return ResponseEntity.status(404).build()
    }

    @Operation(summary = "Salve um aluno", description = "Salve um aluno com as informações dele.")
    @ApiResponses(value = [
        ApiResponse(responseCode = "201", description = "Criado com sucesso"),
        ApiResponse(responseCode = "401", description = "Erro no nível de acesso no corpo da requisição")
    ])
    @PostMapping("/aluno")
    fun salvaAluno(@RequestBody @Valid novoAluno: Usuario): ResponseEntity<Usuario>{
        val nivelAcesso = buscaNivelAcesso(novoAluno.nivelAcesso.id)
        if(nivelAcesso.nome !== NivelAcessoNome.ALUNO) return ResponseEntity.status(401).build()
        val usuarioSalvo = usuarioRepository.save(novoAluno)
        return ResponseEntity.status(201).body(usuarioSalvo)
    }

    @Operation(summary = "Edite um aluno", description = "Edite um aluno com as informações dele no corpo e o id no parâmetro.")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Aluno editado"),
        ApiResponse(responseCode = "404", description = "Aluno não existe"),
        ApiResponse(responseCode = "401", description = "Erro no nível de acesso no parâmetro ou corpo da requisição")
    ])
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

    @Operation(summary = "Delete um aluno", description = "Delete um aluno com base no id dele.")
    @ApiResponses(value = [
        ApiResponse(responseCode = "204", description = "Aluno deletado com sucesso"),
        ApiResponse(responseCode = "401", description = "Erro de nível de acesso no parâmetro da requisição"),
        ApiResponse(responseCode = "404", description = "Aluno não encontrado")
    ])
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

    @Operation(summary = "Busque os alunos", description = "Busque todos os alunos.")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Alunos buscados com sucesso"),
        ApiResponse(responseCode = "204", description = "Nenhum aluno encontrado")
    ])
    @GetMapping("/aluno")
    fun buscaAlunos(): ResponseEntity<List<Usuario>>{
        val listaAlunos = usuarioRepository.findByNivelAcessoNome(NivelAcessoNome.ALUNO)
        if(listaAlunos.isEmpty()){
            return ResponseEntity.status(204).build()
        }
        return ResponseEntity.status(200).body(listaAlunos)
    }
    @Operation(summary = "Salve um professor", description = "Salve um professor com as informações dele.")
    @ApiResponses(value = [
        ApiResponse(responseCode = "201", description = "Professor criado com sucesso"),
        ApiResponse(responseCode = "401", description = "Erro no nível de acesso no corpo da requisição")
    ])
    @PostMapping("/professor")
    fun salvaProfessor(@RequestBody @Valid novoProfessor: Usuario): ResponseEntity<Usuario>{

        val nivelAcesso = buscaNivelAcesso(novoProfessor.nivelAcesso.id)
        if(nivelAcesso.nome !== NivelAcessoNome.PROFESSOR_AUXILIAR) return ResponseEntity.status(401).build()
        val usuarioSalvo = usuarioRepository.save(novoProfessor)
        return ResponseEntity.status(201).body(usuarioSalvo)
    }
    @Operation(summary = "Busque os professores", description = "Busque todos os professores.")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Professores buscados com sucesso"),
        ApiResponse(responseCode = "204", description = "Nenhum professor encontrado")
    ])
    @GetMapping("/professor")
    fun buscaProfessores(): ResponseEntity<List<Usuario>>{
        val listaProfessores = usuarioRepository.findByNivelAcessoNome(NivelAcessoNome.PROFESSOR_AUXILIAR)
        if(listaProfessores.isEmpty()){
            return ResponseEntity.status(204).build()
        }
        return ResponseEntity.status(200).body(listaProfessores)
    }


    @Operation(summary = "Edite um professor", description = "Edite um professor com as informações dele no corpo e o id no parâmetro.")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Professor editado"),
        ApiResponse(responseCode = "404", description = "Professor não existe"),
        ApiResponse(responseCode = "401", description = "Erro no nível de acesso no parâmetro ou corpo da requisição")
    ])
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

    @Operation(summary = "Delete um professor", description = "Delete um professor com base no id dele.")
    @ApiResponses(value = [
        ApiResponse(responseCode = "204", description = "Professor deletado com sucesso"),
        ApiResponse(responseCode = "401", description = "Erro de nível de acesso no parâmetro da requisição"),
        ApiResponse(responseCode = "404", description = "Professor não encontrado")
    ])
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

    @Operation(summary = "Busque o/os representante legais", description = "Busque todos representante legais.")
    @ApiResponses(value = [
        ApiResponse(responseCode = "204", description = "Nenhum representante legal encontrado"),
        ApiResponse(responseCode = "200", description = "Representante legal buscado")
    ])
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