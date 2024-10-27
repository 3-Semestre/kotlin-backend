package grupo9.eduinovatte.domain.service

import grupo9.eduinovatte.domain.model.entity.UsuarioNivelIngles

interface UsuarioNivelInglesService {

    fun salvar(novoUsuarioNivelIngles: UsuarioNivelIngles): UsuarioNivelIngles
    fun removerPorUsuario(id: Int): Int

    fun buscaPorNivelIngles(id: Int): List<UsuarioNivelIngles>

    fun buscaNiveisIngles(): List<UsuarioNivelIngles>

    fun buscaPorIdUsuario(id: Int): List<UsuarioNivelIngles>

    fun deleta(id: Int)

    fun atualizarNivelUsuario(idUsuario: Int, idNivelIngles: Int) : UsuarioNivelIngles
}
