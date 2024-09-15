package grupo9.eduinovatte.controller

import grupo9.eduinovatte.application.dto.request.FiltroAgendamentoForm
import grupo9.eduinovatte.application.dto.request.FiltroForm
import grupo9.eduinovatte.application.dto.request.LoginForm
import grupo9.eduinovatte.application.dto.response.UsuarioResponse
import grupo9.eduinovatte.domain.model.entity.Usuario
import grupo9.eduinovatte.domain.service.NivelAcessoService
import grupo9.eduinovatte.domain.service.SituacaoService
import grupo9.eduinovatte.domain.service.UsuarioService
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
import grupo9.eduinovatte.infraestructure.security.TokenService
import grupo9.eduinovatte.model.enums.SituacaoNome
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/usuarios")
class UsuarioController(
    val usuarioRepository: UsuarioRepository,
    val usuarioService: UsuarioService,
    val tokenService: TokenService,
    val situacaoService: SituacaoService,
    val nivelAcessoService: NivelAcessoService
){

    @Operation(summary = "Autentique o usuário", description = "Autentique o usuário com base no tipo dele (aluno, professor ou representante legal).")
    @ApiResponses(value = [
        ApiResponse(responseCode = "201", description = "Autenticação com sucesso"),
        ApiResponse(responseCode = "403", description = "Erro no login"),
        ApiResponse(responseCode = "401", description = "Erro no nível de acesso")
    ])

    @PostMapping("/autenticar")
    @CrossOrigin
    fun autenticarUsuario(
        @RequestBody loginForm: LoginForm
    ): ResponseEntity<UsuarioResponse>{
        try {
            val usuario = usuarioRepository.findByEmailOrCpfAndSenha(loginForm.email, loginForm.cpf, loginForm.senha)
            val permissao = situacaoService.validaPermissao(usuario.situacao?.id!!, SituacaoNome.ATIVO.name)
            if(permissao == false) throw ResponseStatusException(HttpStatusCode.valueOf(401))

            val novoUsuario = usuarioService.autenticar(usuario.id!!)
            val token: String = tokenService.generateToken(usuario)
            return ResponseEntity.status(201).body(novoUsuario.copy(autenticado = true, token = token))

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
            val permissao = nivelAcessoService.validaPermissao(usuarioDesautenticado.id!!, tipoAcesso!!.name)
            if(!permissao) throw ResponseStatusException(HttpStatusCode.valueOf(401))

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
        val listaUsuarios = usuarioService.buscaUsuarios(tipoAcesso)

        if (listaUsuarios.isEmpty()) {
            throw ResponseStatusException(HttpStatus.NO_CONTENT)
        }

        return ResponseEntity.status(200).body(listaUsuarios)
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
        val permissao = nivelAcessoService.validaPermissao(novoUsuario.nivelAcesso!!.id, tipoAcesso!!.name)
        if(permissao == false) throw ResponseStatusException(HttpStatusCode.valueOf(401))

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
        @RequestBody novoUsuario: Usuario
    ):
            ResponseEntity<UsuarioResponse> {
        val tipoAcesso = retornaNivelAcessoNome(tipo)
        if (usuarioRepository.existsById(id)) {
            val usuarioAntigo = usuarioRepository.findById(id).get()

            if(usuarioAntigo.nivelAcesso!!.id !== novoUsuario.nivelAcesso!!.id) return ResponseEntity.status(401).build()

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
        if (usuarioRepository.existsById(id)) {
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
            "representante_legal" -> usuarioService.exibirPerfil(id)
            else -> return ResponseEntity.status(401).build()
        }

        return ResponseEntity.status(200).body(perfil)
    }
    @CrossOrigin
    @PostMapping("/filtro/{tipo}")
    fun filtraUsuario(@PathVariable tipo: String, @RequestBody filtro: FiltroForm): ResponseEntity<List<Usuario>>{
        val lista = when (tipo) {
            "aluno" -> usuarioService.filtrarAluno(filtro)
            "professor" -> usuarioService.filtrarProfessor(filtro)
            "professor_auxiliar" -> usuarioService.filtrarProfessor(filtro)
            else -> return ResponseEntity.status(401).build()
        }

        return ResponseEntity.status(200).body(lista)
    }

}