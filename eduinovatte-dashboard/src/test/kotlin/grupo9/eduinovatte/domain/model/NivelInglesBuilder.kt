package com.example.demo.builder

import grupo9.eduinovatte.model.NivelIngles
import grupo9.eduinovatte.model.enums.NivelInglesNome

class NivelInglesBuilder {
    private var id: Int = 1
    private var nome: NivelInglesNome? = NivelInglesNome.A1

    companion object {

        // Método para retornar uma lista de todos os níveis de inglês
        fun todosOsNiveisIngles(): List<NivelIngles> {
            return NivelInglesNome.values().mapIndexed { index, nivelInglesNome ->
                NivelIngles(index + 1, nivelInglesNome)
            }
        }
    }

    fun withId(id: Int) = apply { this.id = id }
    fun withNome(nome: NivelInglesNome?) = apply { this.nome = nome }

    fun build(): NivelIngles {
        return NivelIngles(id = id, nome = nome)
    }

    fun basico() = NivelIngles(1, NivelInglesNome.A1)
    fun intermediario() = NivelIngles(1, NivelInglesNome.B1)
    fun avancado() = NivelIngles(1, NivelInglesNome.C1)
    fun fluente() = NivelIngles(1, NivelInglesNome.C2)
}
