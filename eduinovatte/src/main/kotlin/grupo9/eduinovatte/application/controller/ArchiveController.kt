package grupo9.eduinovatte.controller

import grupo9.eduinovatte.application.dto.request.UsuarioCompletoRequest
import grupo9.eduinovatte.application.dto.response.UsuarioResponse
import grupo9.eduinovatte.domain.model.entity.Nicho
import grupo9.eduinovatte.domain.model.entity.NivelAcesso
import grupo9.eduinovatte.domain.model.entity.NivelIngles
import grupo9.eduinovatte.domain.model.entity.Usuario
import grupo9.eduinovatte.domain.service.UsuarioService
import grupo9.eduinovatte.model.enums.NivelAcessoNome
import grupo9.eduinovatte.service.NichoRepository
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.server.ResponseStatusException
import java.io.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.concurrent.ArrayBlockingQueue

@RestController
@RequestMapping("/archive")
class ArchiveController (
    val nichoRepository: NichoRepository,
    val usuarioService: UsuarioService,
    val nivelInglesController: NivelInglesController,
    val nichoController: NichoController
){
    @Operation(summary = "Busque os nichos", description = "Busque todos os nichos dos professores e alunos.")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Nichos encontrados"),
        ApiResponse(responseCode = "204", description = "Nenhum nicho encontrado")
    ])
    @PostMapping("/dashboard/exportar")
    @CrossOrigin
    fun exportaDashboard(): ResponseEntity<List<Nicho>> {
        val nichos = nichoRepository.findAll()
        if(nichos.isEmpty()) return ResponseEntity.status(204).build()
        return ResponseEntity.status(200).body(nichos)
    }

    @Operation(summary = "Importa um usuario", description = "Importe um usuário por meio de um CSV.")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Nichos encontrados"),
        ApiResponse(responseCode = "204", description = "Nenhum nicho encontrado")
    ])
    @PostMapping("/txt/usuarios")
    @CrossOrigin
    fun importaUsuarios(@RequestParam("file") file: MultipartFile): ResponseEntity<Any?> {
        val inputStream = file.inputStream

        lerTxtUsuario(inputStream)

        return ResponseEntity.status(200).build()
    }

    @Operation(summary = "Exporta os usuarios", description = "Exporta os usuários pelo tipo.")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Exporta usuarios"),
        ApiResponse(responseCode = "204", description = "Nenhum usuario encontrado")
    ])
    @GetMapping("/csv/usuarios/{tipo}")
    @CrossOrigin
    fun exportaUsuarios(@PathVariable tipo: String): ResponseEntity<Any?> {
        val tipoAcesso = retornaNivelAcessoNome(tipo)
        val listaUsuarios = usuarioService.buscaUsuariosPorAcesso(tipoAcesso)

        if (listaUsuarios.isEmpty()) {
            throw ResponseStatusException(HttpStatus.NO_CONTENT)
        }
        val arquivo = gravarCsvUsuario(listaUsuarios)

        return ResponseEntity.status(200).body(arquivo)
    }

    private fun gravarCsvUsuario(listaUsuario: List<Usuario>): File{
        val arquivo = FileWriter("usuarios.csv")
        val saida = Formatter(arquivo)


        saida.format("ID;Nome;CPF;Telefone;Data de Nascimento;Email;Profissão;Senha;Nível de Inglês;Nicho;Nível de Acesso;Metas\n")

        for (usuario in listaUsuario) {
            saida.format(
                "%d;%s;%s;%s;%s;%s;%s;%s\n",
                usuario.id,
                usuario.nomeCompleto,
                usuario.cpf,
                usuario.telefone,
                usuario.dataNascimento,
                usuario.email,
                usuario.profissao,
                usuario.senha,
                //usuario.nivelIngles?.name,
                //usuario.nicho?.name,
                usuario.nivelAcesso?.nome?.name,
                //usuario.metas
            )
        }

        saida.close()
        arquivo.close()

        println(arquivo)
        println(saida)
        return File("usuarios.csv")
    }

    fun lerCsv(nome: String){
        val arquivo = FileReader(nome)
        val leitor = Scanner(arquivo).useDelimiter(";|\\n")

        println(String.format("%-5S %-10S %-10S %-10S", "ID", "NOME", "PORTE", "PESO"))

        while(leitor.hasNext()){
            val id = leitor.nextInt()
            val nome = leitor.next()
            val porte = leitor.next()
            val peso = leitor.nextDouble()

            println(String.format("%05d %-10.10s %-10s %5.2f", id, nome, porte, peso))
        }

        leitor.close()
        arquivo.close()
    }
    fun lerTxtUsuario(input: InputStream){
        val leitor = Scanner(BufferedReader(InputStreamReader(input)))

        val listaUsuarios = mutableListOf<UsuarioCompletoRequest>()

        val formatterDate = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        while (leitor.hasNext()) {
            val linha = leitor.nextLine()
            val registro = linha.substring(0, 2)

            if (registro == "00") {
                val conteudo = linha.substring(2, 9)
                val versao = linha.substring(9, 11)

                println("Conteúdo do arquivo: $conteudo")
                println("Versão: $versao")
            } else if (registro == "02") {

                val nomeCompleto = linha.substring(2, 22).trim()
                val cpf = linha.substring(22, 36).trim()
                val telefone = linha.substring(36, 50).trim()
                val dataNascimentoStr = linha.substring(50, 60).trim()
                val email = linha.substring(60, 90).trim()
                val profissao = linha.substring(90, 120).trim()
                val senha = linha.substring(120, 128).trim()
                val nivelIngles = linha.substring(128, 130).trim()
                val nicho = linha.substring(130, 151).trim()
                val nivelAcesso = linha.substring(151, 152).toInt()
                val metas = linha.substring(152, 154).trim().toInt()

                val dataNascimento: LocalDate = LocalDate.parse(dataNascimentoStr, formatterDate)

                // Exibindo os dados no console (ou processando conforme necessário)
                println("Nome Completo: $nomeCompleto")
                println("CPF: $cpf")
                println("Telefone: $telefone")
                println("Data de Nascimento: $dataNascimento")
                println("Email: $email")
                println("Profissão: $profissao")
                println("Senha: $senha")
                println("Nível de Inglês: $nivelIngles")
                println("Nicho: $nicho")
                println("Nível de Acesso: $nivelAcesso")
                println("Metas: $metas")

                val nichos = nichoController.buscaNichos()
                val nichoEscolhido = nichos.body?.find { it.nome!!.name.uppercase() == nicho.uppercase() }
                val filaNicho = ArrayBlockingQueue<Nicho>(1)
                nichoEscolhido?.let { filaNicho.put(it) }

                val niveis = nivelInglesController.buscaNiveis()
                val nivelEscolhido = niveis.body?.find { it.nome!!.name.uppercase() == nivelIngles.uppercase() }
                val filaNivel = ArrayBlockingQueue<NivelIngles>(1)
                nivelEscolhido?.let { filaNivel.put(it) }
                val nivelAcessoNome: NivelAcesso

                if(nivelAcesso == 1){
                    nivelAcessoNome = NivelAcesso(id = 1, NivelAcessoNome.ALUNO)
                } else if(nivelAcesso == 2) {
                    nivelAcessoNome = NivelAcesso(id = 2, NivelAcessoNome.PROFESSOR_AUXILIAR)
                } else {
                    throw ResponseStatusException(HttpStatusCode.valueOf(400))
                }


                 val usuario = UsuarioCompletoRequest(
                    nomeCompleto = nomeCompleto,
                    cpf = cpf,
                    telefone = telefone,
                    dataNascimento = dataNascimento,
                    email = email,
                    profissao = profissao,
                    senha = senha,
                    nivelAcesso = nivelAcessoNome,
                    listaDeNichos = filaNicho,
                    listaDeNiveis = filaNivel
                )

                try {
                    val usuarioSalvo = usuarioService.salvaUsuario(usuario)
                } catch (e: Exception) {
                    throw ResponseStatusException(HttpStatusCode.valueOf(400))
                }

                listaUsuarios.add(usuario)

            } else if (registro == "01") {
                val qtdRegistros = linha.substring(2, 4).toInt()

                if (qtdRegistros == listaUsuarios.size) {
                    println("Quantidade de registros " +
                            "corresponde ao valor informado")
                } else {
                    println("Quantidade de registros não " +
                            "corresponde ao valor informado")
                }
            }
        }

        leitor.close()
    }

    fun retornaNivelAcessoNome(tipo: String): NivelAcessoNome? {
        val tipoAcesso = when (tipo) {
            "aluno" -> NivelAcessoNome.ALUNO
            "professor" -> NivelAcessoNome.PROFESSOR_AUXILIAR
            "representante-legal" -> NivelAcessoNome.REPRESENTANTE_LEGAL
            else -> null
        }
        return tipoAcesso
    }
}