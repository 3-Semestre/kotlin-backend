package grupo9.eduinovatte.domain.service

import grupo9.eduinovatte.domain.model.entity.HorarioProfessor

interface HorarioProfessorService {

    fun salvar(novoHorarioProfessor: HorarioProfessor): HorarioProfessor

    fun buscaPorUsuario(id: Int): HorarioProfessor

    fun edita(horario: HorarioProfessor): HorarioProfessor

    fun buscaHorarios(): List<HorarioProfessor>

    fun deleta(id: Int)
}
