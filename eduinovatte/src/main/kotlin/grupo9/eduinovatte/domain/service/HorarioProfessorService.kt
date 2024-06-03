package grupo9.eduinovatte.domain.service

import grupo9.eduinovatte.domain.repository.HorarioProfessorRepository
import grupo9.eduinovatte.model.HorarioProfessor
import grupo9.eduinovatte.service.UsuarioRepository
import org.springframework.stereotype.Service

@Service
class HorarioProfessorService (
    val horarioProfessorRepository: HorarioProfessorRepository
){
    fun buscarHorarios(): List<HorarioProfessor> {
        return horarioProfessorRepository.findAll()
    }

    /*
    fun adicionaHorario(): HorarioProfessor{
        return horarioProfessorRepository.save(adicionaHorario)
    }
    */
}
