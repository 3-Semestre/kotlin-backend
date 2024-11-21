package grupo9.eduinovatte.controller

import grupo9.eduinovatte.domain.model.entity.Nicho
import grupo9.eduinovatte.service.NichoRepository
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.io.*
import java.util.*

@RestController
@RequestMapping("/import")
class ImportController (
    val nichoRepository: NichoRepository
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
    fun importaUsuarios(@RequestParam("file") file: MultipartFile): Any? {
        val inputStream = file.inputStream
        lerCsvTeste(inputStream)

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
    fun lerCsvTeste(input: InputStream){
        val leitor = Scanner(input).useDelimiter(";|\\n")

        println(String.format("%-5S %-10S %-10S %-10S", "ID", "NOME", "PORTE", "PESO"))

        while(leitor.hasNext()){
            val id = leitor.nextInt()
            val nome = leitor.next()
            val porte = leitor.next()
            val peso = leitor.nextDouble()

            println(String.format("%05d %-10.10s %-10s %5.2f", id, nome, porte, peso))
        }

        leitor.close()
    }


}