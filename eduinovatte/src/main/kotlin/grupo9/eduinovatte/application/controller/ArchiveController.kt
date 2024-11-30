package grupo9.eduinovatte.controller

import grupo9.eduinovatte.application.dto.request.UsuarioCompletoRequest
import grupo9.eduinovatte.domain.model.entity.Nicho
import grupo9.eduinovatte.domain.model.entity.NivelAcesso
import grupo9.eduinovatte.domain.model.entity.Usuario
import grupo9.eduinovatte.model.enums.NivelAcessoNome
import grupo9.eduinovatte.service.NichoRepository
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
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
@RequestMapping("/import")
class ArchiveController (
    val nichoRepository: NichoRepository,
    val usuarioController: UsuarioController
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
    @PostMapping("/txt/usuarios/:tipo")
    @CrossOrigin
    fun importaUsuarios(@RequestParam("file") file: MultipartFile, @PathVariable tipo: String): Any? {
        val tipoAcesso = retornaNivelAcessoNome(tipo)
        val inputStream = file.inputStream

        if(tipoAcesso == NivelAcessoNome.ALUNO){
            lerTxtAluno(inputStream)
        } else if(tipoAcesso == NivelAcessoNome.PROFESSOR_AUXILIAR){
        } else {
            return ResponseEntity.status(400).body("Tipo de usuário inválido")
        }

        return ResponseEntity.status(200)
    }

    private fun gravarCsv(nome: String): File{
        val arquivo = FileWriter(nome)
        val saida = Formatter(arquivo)


        saida.format("%d;%s;%s;%.2f\n", 2,
                "Almir", "Grande", 100.0)


        saida.close()
        arquivo.close()

        println(arquivo)
        println(saida)
        return File(nome)
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
    fun lerTxtAluno(input: InputStream){
        val leitor = Scanner(BufferedReader(InputStreamReader(input)))

        val listaAlunos = mutableListOf<UsuarioCompletoRequest>()

        val formatterDate = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        while (leitor.hasNext()) {
            val linha = leitor.nextLine()
            val registro = linha.substring(0, 2)

            if (registro == "00") {
                val conteudo = linha.substring(2, 7)
                val versao = linha.substring(7, 9)

                println("Conteúdo do arquivo: $conteudo")
                println("Versão: $versao")
            } else if (registro == "02") {

                val nomeCompleto = linha.substring(2, 22).trim()
                val cpf = linha.substring(22, 36).trim()
                val telefone = linha.substring(36, 50).trim()
                val dataNascimentoStr = linha.substring(50, 60).trim()
                val email = linha.substring(60, 90).trim()
                val profissao = linha.substring(90, 100).trim()
                val senha = linha.substring(100, 108).trim()
                val nivelIngles = linha.substring(108, 110).trim()
                val nicho = linha.substring(110, 130).trim()

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

                val nichos = nichoController.buscaNichos()

                val nichoEscolhido = nichos.body?.find { it.nome!!.name == nicho }
                val filaNicho = ArrayBlockingQueue<Nicho>(1)
                filaNicho.put(nichoEscolhido)

                val usuario = UsuarioCompletoRequest(
                    nomeCompleto = nomeCompleto,
                    cpf = cpf,
                    telefone = telefone,
                    dataNascimento = dataNascimento,
                    email = email,
                    profissao = profissao,
                    senha = senha,
                    nivelAcesso = NivelAcesso(id = 1, NivelAcessoNome.ALUNO),
                    listaDeNichos = filaNicho,

                )
                listaAlunos.add(usuario)
                val usuarioSalvo = usuarioController.salvaUsuarioCompleto("aluno", usuario)

                if(usuarioSalvo.statusCode !== HttpStatusCode.valueOf(201)) {
                    throw ResponseStatusException(HttpStatusCode.valueOf(400))
                }

            } else if (registro == "01") {
                val qtdRegistros = linha.substring(2, 12).toInt()

                if (qtdRegistros == listaAlunos.size) {
                    println("Quantidade de registros " +
                            "corresponde ao valor informado")
                } else {
                    println("Quantidade de registros não " +
                            "corresponde ao valor informado")
                }
            }
        }

        leitor.close()

        for (aluno in listaAlunos) {
            println(aluno)
        }
    }
    private fun retornaNivelAcessoNome(tipo: String): NivelAcessoNome? {
        val tipoAcesso = when (tipo) {
            "aluno" -> NivelAcessoNome.ALUNO
            "professor" -> NivelAcessoNome.PROFESSOR_AUXILIAR
            else -> null
        }
        return tipoAcesso
    }

}