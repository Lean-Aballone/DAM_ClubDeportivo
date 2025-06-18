package com.example.clubdeportivo.entidades

import com.example.clubdeportivo.Deporte

data class E_Actividades (
    val id: Int,
    val deporte: Deporte,
    val dias: Array<String>,
    val HorarioInicio: String,
    val HorarioFin: String
)