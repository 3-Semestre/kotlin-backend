package grupo9.eduinovatte.controller

import grupo9.eduinovatte.domain.repository.HorarioProfessorRepository
import grupo9.eduinovatte.domain.model.entity.HorarioProfessor
import org.springframework.http.HttpStatusCode
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException


@Service
class HorarioProfessorService(
    val horarioProfessorRepository: HorarioProfessorRepository
) {

    fun salvar(novoHorarioProfessor: HorarioProfessor): HorarioProfessor {
        if(horarioProfessorRepository.existsById(novoHorarioProfessor.usuario.id!!)){
            throw ResponseStatusException(HttpStatusCode.valueOf(409), "O Horario desse professor já existe")
        }
        val horario = horarioProfessorRepository.save(novoHorarioProfessor)

        return horario
    }

    fun buscaPorUsuario(id: Int): HorarioProfessor {
        val horario = horarioProfessorRepository.findByUsuarioId(id)

        return horario
    }

    fun edita(horario: HorarioProfessor): HorarioProfessor {
        return if(horarioProfessorRepository.existsById(horario.id!!)){
            horarioProfessorRepository.save(horario)
        } else {
            throw ResponseStatusException(HttpStatusCode.valueOf(204))
        }
    }

    fun buscaHorarios(): List<HorarioProfessor>? {
        val horarios = horarioProfessorRepository.findAll()

        if(horarios.isEmpty()) throw ResponseStatusException(HttpStatusCode.valueOf(204))

        return horarios
    }

    fun deleta(id: Int) {
        val deletado = horarioProfessorRepository.deleteById(id)

        return deletado
    }

}

