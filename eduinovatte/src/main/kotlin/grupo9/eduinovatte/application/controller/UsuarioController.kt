package grupo9.eduinovatte.controller

import grupo9.eduinovatte.application.dto.request.LoginForm
import grupo9.eduinovatte.application.dto.response.UsuarioResponse
import grupo9.eduinovatte.domain.service.UsuarioService
import grupo9.eduinovatte.model.*
import grupo9.eduinovatte.model.enums.NivelAcessoNome
import grupo9.eduinovatte.model.enums.SituacaoNome
import grupo9.eduinovatte.service.NivelAcessoRepository
import grupo9.eduinovatte.service.SituacaoRepository
import grupo9.eduinovatte.service.UsuarioRepository
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
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
    val nivelAcessoRepository: NivelAcessoRepository,
    val situacaoRepository: SituacaoRepository,
    val usuarioService: UsuarioService
){

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
            val situacao = buscaSituacao(usuario.situacao?.id)

            if(situacao?.nome == SituacaoNome.INATIVO) {
                usuarioRepository.desautenticar(usuario.id)
                return ResponseEntity.status(401).build()
            }

            val tipoAcesso = verificaNivelAcesso(tipo)

            if(nivelAcesso.nome == tipoAcesso){
                usuarioRepository.autenticar(usuario.id)
                val novoUsuario = usuarioRepository.findById(usuario.id!!).get()
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
            val tipoAcesso = verificaNivelAcesso(tipo)

            if(nivelAcesso.nome !== tipoAcesso) return ResponseEntity.status(401).build()
            usuarioRepository.desautenticar(id)
            return ResponseEntity.status(200).build()
        }
        return ResponseEntity.status(404).build()
    }

    @Operation(summary = "Busque os usuários", description = "Busque todos os professores.")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Professores buscados com sucesso"),
        ApiResponse(responseCode = "204", description = "Nenhum professor encontrado")
    ])
    @GetMapping("/{tipo}")
    fun buscaUsuarios(@PathVariable tipo: String): ResponseEntity<List<UsuarioResponse>>{
        val tipoAcesso = verificaNivelAcesso(tipo)
        val listaProfessores = usuarioService.buscaProfessores(tipoAcesso)

        return ResponseEntity.status(200).body(listaProfessores)
    }
    @Operation(summary = "Salve um aluno", description = "Salve um aluno com as informações dele.")
    @ApiResponses(value = [
        ApiResponse(responseCode = "201", description = "Criado com sucesso"),
        ApiResponse(responseCode = "401", description = "Erro no nível de acesso no corpo da requisição")
    ])
    @PostMapping("/{tipo}/teste")
    fun salvaUsuario(
        @PathVariable tipo: String,
        @RequestBody @Valid novoUsuario: Usuario
    ): ResponseEntity<Usuario>{
        val tipoAcesso = verificaNivelAcesso(tipo)
        val nivelAcesso = buscaNivelAcesso(novoUsuario.nivelAcesso.id)
        if(nivelAcesso.nome !== tipoAcesso) return ResponseEntity.status(401).build()
        val usuarioExistente = usuarioRepository.findByCpf(novoUsuario.cpf)
        if (usuarioExistente != null) {
            return ResponseEntity.status(409).body(usuarioExistente) // Status 409 Conflict
        }
        val usuarioSalvo = usuarioRepository.save(novoUsuario)
        return ResponseEntity.status(201).body(usuarioSalvo)
    }

    @Operation(summary = "Edite um aluno", description = "Edite um aluno com as informações dele no corpo e o id no parâmetro.")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Aluno editado"),
        ApiResponse(responseCode = "404", description = "Aluno não existe"),
        ApiResponse(responseCode = "401", description = "Erro no nível de acesso no parâmetro ou corpo da requisição")
    ])
    @PutMapping("/{tipo}}/{id}")
    fun editaUsuario(
        @PathVariable tipo:String,
        @PathVariable id: Int,
        @RequestBody novoUsuario: Usuario):
            ResponseEntity<Usuario> {
        val tipoAcesso = verificaNivelAcesso(tipo)
        if (usuarioRepository.existsById(id)) {
            val usuarioAntigo = usuarioRepository.findById(id).get()
            val nivelAcessoUsuarioAntigo = buscaNivelAcesso(usuarioAntigo.nivelAcesso.id)
            val nivelAcessoNovoUsuario = buscaNivelAcesso(novoUsuario.nivelAcesso.id)
            if(nivelAcessoNovoUsuario.nome !== tipoAcesso || nivelAcessoUsuarioAntigo.nome !== tipoAcesso) return ResponseEntity.status(401).build()
            novoUsuario.id = id
            usuarioRepository.save(novoUsuario)
            return ResponseEntity.status(200).body(novoUsuario)
        }
        return ResponseEntity.status(404).build()
    }

    @DeleteMapping("/{tipo}}/{id}")
    fun deletaUsuario(
        @PathVariable tipo:String,
        @PathVariable id: Int):ResponseEntity<Void> {
        val tipoAcesso = verificaNivelAcesso(tipo)
        if (usuarioRepository.existsById(id)) {
            val usuario = usuarioRepository.findById(id).get()
            val nivelAcesso = buscaNivelAcesso(usuario.nivelAcesso.id)
            if(nivelAcesso.nome !== tipoAcesso) return ResponseEntity.status(401).build()
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

    @Operation(summary = "Desative um usuário", description = "Desative um usuário pelo ID.")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Usuario desativado"),
        ApiResponse(responseCode = "404", description = "Usuario não existe")
    ])
    @PutMapping("/desativar/{id}")
    fun desativaAluno(
        @PathVariable id: Int):
            ResponseEntity<Int> {

        if (usuarioRepository.existsById(id)) {
            val retorno = usuarioRepository.desativar(id)
            return ResponseEntity.status(200).body(retorno)
        }
        return ResponseEntity.status(404).build()
    }

    fun buscaNivelAcesso(id:Int): NivelAcesso{
        return nivelAcessoRepository.findById(id).get()
    }

    fun buscaSituacao(id:Int?): Situacao?{
        if(id !== null) return situacaoRepository.findById(id).get()
        return null
    }

    fun verificaNivelAcesso(tipo: String): NivelAcessoNome?{
         val tipoAcesso = when (tipo) {
            "aluno" -> NivelAcessoNome.ALUNO
            "professor" -> NivelAcessoNome.PROFESSOR_AUXILIAR
            "representante-legal" -> NivelAcessoNome.REPRESENTANTE_LEGAL
            else -> null
        }
        return tipoAcesso
    }
}