package grupo9.eduinovatte.domain.service.impl

import grupo9.eduinovatte.domain.repository.HorarioProfessorRepository
import grupo9.eduinovatte.domain.model.entity.HorarioProfessor
import grupo9.eduinovatte.domain.service.HorarioProfessorService
import org.springframework.http.HttpStatusCode
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class HorarioProfessorServiceImpl(
    private val horarioProfessorRepository: HorarioProfessorRepository
) : HorarioProfessorService {

    override fun salvar(novoHorarioProfessor: HorarioProfessor): HorarioProfessor {
        if (horarioProfessorRepository.existsById(novoHorarioProfessor.usuario.id!!)) {
            throw ResponseStatusException(HttpStatusCode.valueOf(409), "O Horário desse professor já existe")
        }
        return horarioProfessorRepository.save(novoHorarioProfessor)
    }

    override fun buscaPorUsuario(id: Int): HorarioProfessor {
        return horarioProfessorRepository.findByUsuarioId(id)
            ?: throw ResponseStatusException(HttpStatusCode.valueOf(404), "Horário do professor não encontrado")
    }

    override fun edita(horario: HorarioProfessor): HorarioProfessor {
        return if (horarioProfessorRepository.existsById(horario.id!!)) {
            horarioProfessorRepository.save(horario)
        } else {
            throw ResponseStatusException(HttpStatusCode.valueOf(404), "Horário do professor não encontrado")
        }
    }

    override fun buscaHorarios(): List<HorarioProfessor> {
        val horarios = horarioProfessorRepository.findAll()
        if (horarios.isEmpty()) {
            throw ResponseStatusException(HttpStatusCode.valueOf(204), "Nenhum horário encontrado")
        }
        return horarios
    }

    override fun deleta(id: Int) {
        if (horarioProfessorRepository.existsById(id)) {
            horarioProfessorRepository.deleteById(id)
        } else {
            throw ResponseStatusException(HttpStatusCode.valueOf(404), "Horário do professor não encontrado")
        }
    }
}
