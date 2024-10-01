package com.example.demo.builder

import grupo9.eduinovatte.domain.model.entity.HorarioProfessor
import grupo9.eduinovatte.domain.model.entity.Usuario
import grupo9.eduinovatte.model.UsuarioBuilder
import java.time.LocalTime

class HorarioProfessorBuilder {
    private var id: Int? = 1
    private var inicio: LocalTime = LocalTime.of(9, 0, 0)
    private var fim: LocalTime = LocalTime.of(18, 0, 0)
    private var pausaInicio: LocalTime = LocalTime.of(12, 0, 0)
    private var pausaFim: LocalTime = LocalTime.of(13, 0, 0)
    private var usuario: Usuario = UsuarioBuilder().build()

    fun withId(id: Int) = apply { this.id = id }
    fun withInicio(inicio: LocalTime) = apply { this.inicio = inicio }
    fun withFim(fim: LocalTime) = apply { this.fim = fim }
    fun withPausaInicio(pausaInicio: LocalTime) = apply { this.pausaInicio = pausaInicio }
    fun withPausaFim(pausaFim: LocalTime) = apply { this.pausaFim = pausaFim }
    fun withUsuario(usuario: Usuario) = apply { this.usuario = usuario }

    fun build(): HorarioProfessor {
        return HorarioProfessor(
            id = id,
            inicio = inicio,
            fim = fim,
            pausaInicio = pausaInicio,
            pausaFim = pausaFim,
            usuario = usuario
        )
    }
}
