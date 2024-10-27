package grupo9.eduinovatte.domain.repository.projection

interface AgendamentosDetalhesListagemProjection {
    fun getId(): Int;
    fun getData(): String
    fun getHorario_Inicio(): String;
    fun getHorario_Fim(): String;
    fun getAssunto(): String;
    fun getFk_Professor(): Int;
    fun getNome_Professor(): String;
    fun getFk_Aluno(): Int;
    fun getNome_Aluno(): String;
    fun getStatus_List(): String;
}
