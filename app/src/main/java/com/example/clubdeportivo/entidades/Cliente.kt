package com.example.clubdeportivo.entidades

import java.io.Serializable
import java.util.Date

data class Cliente(
    val id: Int,
    val nombre: String,
    val apellido: String,
    val dni: Int,
    val telefono: String,
    val direccion: String,
    val aptoFisico: Boolean,
    val socio: Boolean,
    val fechaInscripcion: Date
) : Serializable