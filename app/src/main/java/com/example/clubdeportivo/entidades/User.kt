package com.example.clubdeportivo.entidades

import java.io.Serializable

data class User(
    val nombre: String,
    val pass: String,
    val rol: String
) : Serializable