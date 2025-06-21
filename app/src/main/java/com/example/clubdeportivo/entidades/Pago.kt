package com.example.clubdeportivo.entidades

import java.io.Serializable
import java.util.Date

data class Pago(
    val id: Int,
    val idCliente: Int,
    val monto: Int,
    val mes: String?,
    val idActividad: Int,
    val formaPago: String,
    val fechaPago: Date?
) : Serializable
