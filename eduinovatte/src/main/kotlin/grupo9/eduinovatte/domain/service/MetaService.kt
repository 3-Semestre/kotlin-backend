package grupo9.eduinovatte.domain.service

import grupo9.eduinovatte.domain.model.entity.Meta
import grupo9.eduinovatte.domain.model.entity.UsuarioNicho
import java.util.*

interface MetaService {

    fun salvar(novaMeta: Meta): Meta
    fun removerPorProfessor(id: Int): Int

    fun buscaMetas(): List<Meta>

    fun buscaPorProfessor(id: Int): Optional<List<Meta>>

    fun deleta(id: Int)
}
