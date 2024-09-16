package grupo9.eduinovatte.domain.repository

interface AgendamentosDetalhesListagemResponse {
    fun getId(): Int;
    fun getData(): String
    fun getHorario_Inicio(): String;
    fun getHorario_Fim(): String;
    fun getAssunto(): String;
    fun getFk_Professor(): Int;
    fun getFk_Aluno(): Int;
    fun getStatus_List(): String;
}
