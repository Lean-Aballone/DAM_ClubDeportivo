package com.example.clubdeportivo

import android.content.ContentValues
import android.content.Context

class ClientesHelper(context: Context): DBHelper(context) {

    fun inscribirCliente(nombre: String, apellido: String, dni: Int, telefono: String, direccion: String, aptoFisico: Boolean, socio: Boolean): Boolean{
        val db = writableDatabase
        val valores = ContentValues().apply {
            put("Nombre", nombre)
            put("Apellido", apellido)
            put("DNI", dni)
            put("Telefono", telefono)
            put("Direccion", direccion)
            put("AptoFisico", aptoFisico)
            put("Socio", socio)
        }

        val resultado = db.insert("clientes", null, valores)
        return resultado != -1L
    }

//    fun getClienteByDNI(dni: String): Cliente{
//        val db = readableDatabase
//        val cursor = db.rawQuery(
//            "SELECT * FROM clientes WHERE DNI = ?",
//            arrayOf(dni)
//        )
//        cursor.close()
//        return cursor
//    }
}