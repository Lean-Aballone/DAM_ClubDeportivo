package com.example.clubdeportivo

import android.content.ContentValues
import android.content.Context

class ClientesHelper(context: Context): DBHelper(context) {

    fun inscribirCliente(nombre: string, apellido: string, dni: String, telefono: string, direccion, string): Boolean{
        val db = writableDatabase
        val valores = ContentValues().apply {
            put("nombre", nombre)
            put("apellido", apellido)
            put("dni", dni)
            put("telefono", telefono)
            put("direccion", direccion)
        }

        val resultado = db.insert("clientes", null, valores)
        return resultado != -1L
    }

    fun getClienteByDNI(dni: String): Cliente{
        val db = readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM clientes WHERE DNI = ?",
            arrayOf(dni)
        )
        cursor.close()
        return cursor
    }
}