package grupo9.eduinovatte.domain.repository.projection

interface UsuarioPerfilViewProjection : UsuarioPerfilAlunoViewProjection {
    fun getInicio(): String;
    fun getFim(): String;
    fun getPausa_inicio(): String;
    fun getPausa_fim(): String;
    fun getQtd_aula(): Int?;
}
