package grupo9.eduinovatte.domain.service

import grupo9.eduinovatte.domain.model.entity.UsuarioNicho

interface UsuarioNichoService {

    fun salvar(novoUsuarioNicho: UsuarioNicho): UsuarioNicho
    fun removerPorUsuario(id: Int): Int

    fun buscaPorNicho(id: Int): List<UsuarioNicho>

    fun buscaUsuariosNichos(): List<UsuarioNicho>

    fun buscaPorIdUsuario(id: Int): List<UsuarioNicho>

    fun deleta(id: Int)

    fun atualizaNicho(id: Int, idNicho: Int): UsuarioNicho
}
