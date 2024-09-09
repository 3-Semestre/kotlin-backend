package grupo9.eduinovatte.domain.service

interface PermissaoService {
    fun validaPermissao(id: Int, condicao: String): Boolean

}
