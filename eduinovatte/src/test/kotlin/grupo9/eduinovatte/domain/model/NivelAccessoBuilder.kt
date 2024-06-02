package com.example.demo.builder

import grupo9.eduinovatte.model.NivelAcesso
import grupo9.eduinovatte.model.enums.NivelAcessoNome

class NivelAcessoBuilder {
    private var id: Int = 1
    private var nome: NivelAcessoNome? = NivelAcessoNome.ALUNO

    companion object {

        // Método para retornar uma lista de todas as situações
        fun todasOsNiveisAcesso(): List<NivelAcesso> {
            return NivelAcessoNome.values().mapIndexed { index, NivelAcessoNome ->
                NivelAcesso(index + 1, NivelAcessoNome)
            }
        }
    }

    fun withId(id: Int) = apply { this.id = id }
    fun withNome(nome: NivelAcessoNome?) = apply { this.nome = nome }

    fun build():NivelAcesso{
        return NivelAcesso(id = id,nome = nome)
    }

    fun aluno() = NivelAcesso(1, NivelAcessoNome.ALUNO)
    fun professor() = NivelAcesso(2, NivelAcessoNome.PROFESSOR_AUXILIAR)
    fun representante_legal() = NivelAcesso(3, NivelAcessoNome.REPRESENTANTE_LEGAL)
}
