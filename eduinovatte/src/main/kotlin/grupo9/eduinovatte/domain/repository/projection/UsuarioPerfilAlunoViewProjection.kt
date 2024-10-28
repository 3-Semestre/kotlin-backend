package grupo9.eduinovatte.domain.repository.projection

interface UsuarioPerfilAlunoViewProjection {
    fun getId(): Int;
    fun getNome_completo(): String;
    fun getCpf(): String;
    fun getData_nascimento(): String;
    fun getProfissao(): String;
    fun getEmail(): String;
    fun getTelefone(): String;
    fun getSenha(): String;
    fun getNivel_acesso_id(): Long;
    fun getNichos(): String;
    fun getNiveis_Ingles(): String;
}
