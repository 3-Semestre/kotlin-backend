package grupo9.eduinovatte.domain.service

import grupo9.eduinovatte.application.dto.response.UsuarioResponse
import grupo9.eduinovatte.application.integration.ViaCepClient
import grupo9.eduinovatte.model.Usuario
import grupo9.eduinovatte.model.enums.NivelAcessoNome
import grupo9.eduinovatte.service.UsuarioRepository
import org.modelmapper.ModelMapper
import org.springframework.http.HttpStatusCode
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class UsuarioService (
    val usuarioRepository: UsuarioRepository,
    val viaCepClient: ViaCepClient
){
    fun buscaProfessores(): List<UsuarioResponse>{
        val professores = usuarioRepository.findByNivelAcessoNome(NivelAcessoNome.PROFESSOR_AUXILIAR)
        validarLista(professores)
        val professoresResponse = retornaListaUsuario(professores)

        return professoresResponse
    }

    fun retornaListaUsuario(usuarios: List<Usuario>): List<UsuarioResponse>{
        val dtos = usuarios.map{
            UsuarioResponse(id = it.id, nomeCompleto = it.nomeCompleto, cpf = it.cpf, telefone = it.telefone, autenticado = it.autenticado, email = it.email, profissao = it.profissao, nivelAcesso = it.nivelAcesso, situacao = it.situacao)
        }
        return dtos
    }

    fun validarLista(lista:List<*>) {
        if (lista.isEmpty()) {
            throw ResponseStatusException(HttpStatusCode.valueOf(204))
        }
    }
}