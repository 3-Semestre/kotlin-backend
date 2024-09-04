package com.example.demo.builder

import grupo9.eduinovatte.domain.model.entity.Nicho
import grupo9.eduinovatte.model.enums.NichoNome

class NichoBuilder {
    private var id: Int = 1
    private var nome: NichoNome? = NichoNome.TECNICO

    companion object {

        // Método para retornar uma lista de todas as situações
        fun todasOsNichos(): List<Nicho> {
            return NichoNome.values().mapIndexed { index, NichoNome ->
                Nicho(index + 1, NichoNome)
            }
        }
    }

    fun withId(id: Int) = apply { this.id = id }
    fun withNome(nome: NichoNome?) = apply { this.nome = nome }

    fun build(): Nicho {
        return Nicho(id = id,nome = nome)
    }

    fun tecnico() = Nicho(1, NichoNome.TECNICO)
    fun business() = Nicho(1, NichoNome.BUSINESS)
    fun moradores_exterior() = Nicho(1, NichoNome.MORADORES_EXTERIOR)
    fun testes_internacionais() = Nicho(1, NichoNome.TESTES_INTERNACIONAIS)
    fun infantil() = Nicho(1, NichoNome.INFANTIL)
}
