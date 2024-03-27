package grupo9.eduinovatte.controller

import grupo9.eduinovatte.model.Arquivo
import grupo9.eduinovatte.model.ArquivoProfessor
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("upload-arquivo")
class ArquivoProfessorController(var professorController: ProfessorController){

    var imagemString = "iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAACXBIWXMAAAsSAAALEgHS3X78AAACWklEQVQ4y7WTv0tUURjH7+9/c4wzKzAzU1VVxsRQMLFqigIgSpQ4+8Cv2CIGm0D7ASypYgSgQRUxYHxXshdFhFiKrEIc3Jk5jLlWzLFl19/Zt99zvn3nPOfc869TQe2kWIZCOEoGGCIMkDnAmfegRQSkQ5U6ZGca6RmZnyY6Ro9Gm+szMTHc9GiAAAEb0qWkJwe5dnZJj8A4GhkbQBiGswMLDw7mtAf7rSovFYAs3K9vD4IQTiLqmstlsP4/VzWlkb+7wlIVSBZAQDm9v0nOHV4/Pw/Hw+ZkZbWD6FGwWk8zvZ2/f79k1dRVnq0sq8XAGAbSyX6fF4Xl9foolZmT7Z2NjlCSiUdQUAQ8Pf3p3DxZKKvXvA4GKxwdnZmYmLLvZ3l4CUGubm5OLfBqN2djYyilAAAPD8/c3OzsbFZMw2v4rF43GIdJOTk+XjY2NkZGRsXg+PuYx2bSksLO0DtFosWLT8f5yUllZWaEbXXB3t4dP39/c5EMNjfnDwcAeFzAFmHxgYGBsSqVCXFxcbW2trhTgM/N+/fv9//zvfEo/KysrISHR9w9vT08Pjo6OgDIyctQyAKxcTEzM7O6urowMBAxMbG+vz88n3q6Gh4ePjY2NrRs2TLV64mPj5fX19fX19fX19fX19fX19fX19fX19fX19fX19fX19fX19fX19fX19fX19fX19fX19fX19fX19fX19fX19fX19fX19fX19fX19fX19fX19fX19fX19fX19fX19fX19fX19fX19fX19fX19fX19fX19fX19fX19fX19fX19fX19fX19fX19fX19fX19fX19fX19fX19fX19fX19fX19fX19fX19fX19fX19fX19fX19fX19fX19fX19fX19fX19fX19fX19fX19fX19fX19fX19fX19fX19fX19fX19fX19fX19fX19fX19fX19fX19fX19fX19fX19fX19fX19fX19fX19fX19fX19fX19fX19fX19fX19fX19fX19fX19fX19fX19fX19fX19fX19fX19fX19fX19fX1"
    var imagemBinario = imagemString.toByteArray()

    val listaArquivo: MutableList<Arquivo> = mutableListOf()
    @PostMapping("/{indice}")
    fun adicionaImagemProfessor(@PathVariable indice: Int, @RequestBody imagem: MultipartFile): ResponseEntity<Arquivo> {

        /*if(buscarArquivo(indice) != null){
            return ResponseEntity.status(409).build()
        }*/

        val novoArquivo = Arquivo(
            nome = imagem.originalFilename,
            tamanho = imagem.size,
            tipo = imagem.contentType,
            professor = indice,
            dados = imagem.bytes,
        )

        listaArquivo.add(novoArquivo)

        println("Tamanho da imagem:" + imagem.size)
        println("Bytes da imagem:" + imagem.bytes)
        println("Tipo da imagem:" + imagem.contentType)
        println("Recurso da imagem:" + imagem.resource)
        println("Nome do arquivo:" + imagem.originalFilename)
        return ResponseEntity.status(201).body(novoArquivo)
    }

    @GetMapping("/{indice}")
    fun buscaArquivoPorProfessor(@PathVariable indice: Int): ResponseEntity<ArquivoProfessor> {
        if(listaArquivo.isEmpty()) return ResponseEntity.status(204).build()
        var buscaArquivo = buscarArquivo(indice)
        var buscaProfessor = professorController.listaProfessores[indice - 1]

        var arquivoProfessor = ArquivoProfessor(
            professor = buscaProfessor,
            arquivo = buscaArquivo
        )

        return ResponseEntity.status(200).body(arquivoProfessor)
    }

    private fun buscarArquivo(indice: Int): Arquivo? {
        for(i in listaArquivo){
            if (i.professor == indice){
                return i
            }
        }
        return null
    }
}