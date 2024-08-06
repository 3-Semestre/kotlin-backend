package grupo9.eduinovatte.controller

import grupo9.eduinovatte.application.dto.request.LoginForm
import grupo9.eduinovatte.application.dto.response.AgendamentoProximosProjection
import grupo9.eduinovatte.application.dto.response.UsuarioResponse
import grupo9.eduinovatte.domain.repository.UsuarioPerfilViewProjection
import grupo9.eduinovatte.domain.service.UsuarioService
import grupo9.eduinovatte.model.*
import grupo9.eduinovatte.model.enums.NivelAcessoNome
import grupo9.eduinovatte.service.UsuarioRepository
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import jakarta.validation.Valid
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
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
    val usuarioService: UsuarioService
){

    @Operation(summary = "Autentique o usuário", description = "Autentique o usuário com base no tipo dele (aluno, professor ou representante legal).")
    @ApiResponses(value = [
        ApiResponse(responseCode = "201", description = "Autenticação com sucesso"),
        ApiResponse(responseCode = "403", description = "Erro no login"),
        ApiResponse(responseCode = "401", description = "Erro no nível de acesso")
    ])

 //   @PostMapping("/{tipo}/autenticar")
    @PostMapping("/autenticar")
    @CrossOrigin
    fun autenticarUsuario(
        // @PathVariable tipo: String,
        @RequestBody loginForm: LoginForm
    ): ResponseEntity<UsuarioResponse>{
        try {
            val usuario = usuarioRepository.findByEmailOrCpfAndSenha(loginForm.email, loginForm.cpf, loginForm.senha)
            // val tipoAcesso = retornaNivelAcessoNome(tipo)
            usuarioService.validaSituacao(usuario.situacao?.id)
            // usuarioService.validaNivelAcesso(usuario.nivelAcesso!!.id, tipoAcesso)

            val novoUsuario = usuarioService.autenticar(usuario.id!!)
            return ResponseEntity.status(201).body(novoUsuario.copy(autenticado = true))

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
            val usuarioDesautenticado = usuarioRepository.findById(id).get()
            val tipoAcesso = retornaNivelAcessoNome(tipo)
            usuarioService.validaNivelAcesso(usuarioDesautenticado.id, tipoAcesso)

            usuarioService.desautenticar(usuarioDesautenticado.id!!)
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
    @CrossOrigin
    fun buscaUsuarios(@PathVariable tipo: String): ResponseEntity<List<UsuarioResponse>>{
        val tipoAcesso = retornaNivelAcessoNome(tipo)
        val listaProfessores = usuarioService.buscaUsuarios(tipoAcesso)

        return ResponseEntity.status(200).body(listaProfessores)
    }
    @Operation(summary = "Salve um aluno", description = "Salve um aluno com as informações dele.")
    @ApiResponses(value = [
        ApiResponse(responseCode = "201", description = "Criado com sucesso"),
        ApiResponse(responseCode = "401", description = "Erro no nível de acesso no corpo da requisição")
    ])
    @PostMapping("/{tipo}")
    @CrossOrigin
    fun salvaUsuario(
        @PathVariable tipo: String,
        @RequestBody @Valid novoUsuario: Usuario
    ): ResponseEntity<UsuarioResponse>{
        val tipoAcesso = retornaNivelAcessoNome(tipo)
        usuarioService.validaNivelAcesso(novoUsuario.nivelAcesso!!.id, tipoAcesso)

        val usuarioSalvo = usuarioService.salvaUsuario(novoUsuario)
        return ResponseEntity.status(201).body(usuarioSalvo)
    }

    @Operation(summary = "Edite um aluno", description = "Edite um aluno com as informações dele no corpo e o id no parâmetro.")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Aluno editado"),
        ApiResponse(responseCode = "404", description = "Aluno não existe"),
        ApiResponse(responseCode = "401", description = "Erro no nível de acesso no parâmetro ou corpo da requisição")
    ])
    @PutMapping("/{tipo}/{id}")
    @CrossOrigin
    fun editaUsuario(
        @PathVariable tipo:String,
        @PathVariable id: Int,
        @RequestBody novoUsuario: Usuario):
            ResponseEntity<UsuarioResponse> {
        val tipoAcesso = retornaNivelAcessoNome(tipo)
        if (usuarioRepository.existsById(id)) {
            val usuarioAntigo = usuarioRepository.findById(id).get()

            if(usuarioAntigo.nivelAcesso!!.id !== novoUsuario.nivelAcesso!!.id) return ResponseEntity.status(401).build()

            // usuarioService.validaNivelAcesso(novoUsuario.nivelAcesso!!.id, tipoAcesso)

            novoUsuario.id = id
            val usuarioEditado = usuarioService.editaUsuario(novoUsuario)
            return ResponseEntity.status(200).body(usuarioEditado)
        }
        return ResponseEntity.status(404).build()
    }

    @DeleteMapping("/{tipo}/{id}")
    @CrossOrigin
    fun deletaUsuario(
        @PathVariable tipo:String,
        @PathVariable id: Int):ResponseEntity<Void> {
        //val tipoAcesso = retornaNivelAcessoNome(tipo)
        if (usuarioRepository.existsById(id)) {
            //val usuario = usuarioRepository.findById(id).get()
            //usuarioService.validaNivelAcesso(usuario.nivelAcesso!!.id, tipoAcesso)

            usuarioService.deletaUsuario(id)
            return ResponseEntity.status(204).build()
        }
        return ResponseEntity.status(404).build()
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
            val retorno = usuarioService.desativaUsuario(id)
            return ResponseEntity.status(200).body(retorno)
        }
        return ResponseEntity.status(404).build()
    }

    fun retornaNivelAcessoNome(tipo: String): NivelAcessoNome?{
         val tipoAcesso = when (tipo) {
            "aluno" -> NivelAcessoNome.ALUNO
            "professor" -> NivelAcessoNome.PROFESSOR_AUXILIAR
            "representante-legal" -> NivelAcessoNome.REPRESENTANTE_LEGAL
            else -> null
        }
        return tipoAcesso
    }
    
    @Operation(summary = "Busque um usuários pelo id", description = "Busque todos os dados do perfil de um usuario pelo id.")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Professores buscados com sucesso"),
        ApiResponse(responseCode = "204", description = "Nenhum professor encontrado")
    ])
    @CrossOrigin
    @GetMapping("/perfil/{tipo}/{id}")
    fun exibirPerfil(@PathVariable tipo: String, @PathVariable id: Int): ResponseEntity<Any> {
        val perfil = when (tipo) {
            "aluno" -> usuarioService.exibirPerfilAluno(id)
            "professor" -> usuarioService.exibirPerfil(id)
            "professor_auxiliar" -> usuarioService.exibirPerfil(id)
            "representante-legal" -> usuarioService.exibirPerfil(id)
            else -> return ResponseEntity.status(401).build()
        }

        return ResponseEntity.status(200).body(perfil)
    }

}