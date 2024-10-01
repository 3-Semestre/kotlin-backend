package com.example.demo.builder

import grupo9.eduinovatte.domain.model.entity.Situacao
import grupo9.eduinovatte.model.enums.SituacaoNome

class SituacaoBuilder {
    private var id: Int = 0
    private var nome: SituacaoNome? = null

    companion object {

        // Método para retornar uma lista de todas as situações
        fun todasAsSituacoes(): List<Situacao> {
            return SituacaoNome.values().mapIndexed { index, situacaoNome ->
                Situacao(index + 1, situacaoNome)
            }
        }
    }

    fun withId(id: Int) = apply { this.id = id }
    fun withNome(nome: SituacaoNome?) = apply { this.nome = nome }

    fun build(): Situacao {
        return Situacao(id = id,nome = nome)
    }

    fun ativo() = Situacao(1, SituacaoNome.ATIVO)
    fun inativo() = Situacao(2, SituacaoNome.INATIVO)
}
