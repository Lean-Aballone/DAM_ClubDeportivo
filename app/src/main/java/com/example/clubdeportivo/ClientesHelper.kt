package com.example.clubdeportivo

import android.content.ContentValues
import android.content.Context
import com.example.clubdeportivo.entidades.Cliente
import java.text.SimpleDateFormat
import java.util.Locale

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


    fun getClienteByDNIorId(tipo: String, valor: Int): Cliente? {
        var cliente: Cliente? = null

        val query: String = if(tipo == "DNI") {
            "SELECT Id, Nombre, Apellido, DNI, Telefono, Direccion, AptoFisico, Socio, FechaInscripcion FROM clientes WHERE DNI = ? LIMIT 1"
        } else {
            "SELECT Id, Nombre, Apellido, DNI, Telefono, Direccion, AptoFisico, Socio, FechaInscripcion FROM clientes WHERE Id = ? LIMIT 1"
        }

        val db = readableDatabase
        val cursor = db.rawQuery(
            query,
            arrayOf(valor.toString())
        )
        if(cursor.moveToFirst()) {
            val id = cursor.getInt(0)
            val nombre = cursor.getString(1)
            val apellido = cursor.getString(2)
            val dni = cursor.getInt(3)
            val telefono = cursor.getString(4)
            val direccion = cursor.getString(5)
            val aptoFisico = cursor.getInt(6) != 0
            val socio = cursor.getInt(7) != 0
            val fechaInscripcion = cursor.getString(8)

            val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

            cliente = Cliente(
                id = id,
                nombre = nombre,
                apellido = apellido,
                dni = dni,
                telefono = telefono,
                direccion = direccion,
                aptoFisico = aptoFisico,
                socio = socio,
                fechaInscripcion = formatter.parse(fechaInscripcion)
            )
        }
        cursor.close()
        return cliente
    }

    fun getAllClients(): List<Cliente>{
        val clientList = mutableListOf<Cliente>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM clientes",null)
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(0)
                val nombre = cursor.getString(1)
                val apellido = cursor.getString(2)
                val dni = cursor.getInt(3)
                val telefono = cursor.getString(4)
                val direccion = cursor.getString(5)
                val aptoFisico = cursor.getInt(6) != 0
                val socio = cursor.getInt(7) != 0
                val fechaInscripcion = cursor.getString(8)

                val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

                val cliente = Cliente(
                    id = id,
                    nombre = nombre,
                    apellido = apellido,
                    dni = dni,
                    telefono = telefono,
                    direccion = direccion,
                    aptoFisico = aptoFisico,
                    socio = socio,
                    fechaInscripcion = formatter.parse(fechaInscripcion)
                )
                clientList.add(cliente)
            }  while (cursor.moveToNext())
        }
        cursor.close()
        return clientList
    }

    fun getAllSociosDeudores(): List<Cliente>{
        val clientList = mutableListOf<Cliente>()
        val db = readableDatabase
        val query = """
            SELECT clientes.*, pagos.UltimoPago FROM clientes LEFT JOIN (
                SELECT IdCliente, MAX(FechaPago) AS UltimoPago
                FROM pagos
                GROUP BY IdCliente
            ) pagos ON clientes.id = pagos.IdCliente 
            WHERE socio = 1 AND (pagos.UltimoPago IS NULL OR pagos.UltimoPago < datetime('now', '-1 month'));
        """.trimIndent()
        val cursor = db.rawQuery(query,null)
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(0)
                val nombre = cursor.getString(1)
                val apellido = cursor.getString(2)
                val dni = cursor.getInt(3)
                val telefono = cursor.getString(4)
                val direccion = cursor.getString(5)
                val aptoFisico = cursor.getInt(6) != 0
                val socio = cursor.getInt(7) != 0
                val fechaInscripcion = cursor.getString(8)

                val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

                val cliente = Cliente(
                    id = id,
                    nombre = nombre,
                    apellido = apellido,
                    dni = dni,
                    telefono = telefono,
                    direccion = direccion,
                    aptoFisico = aptoFisico,
                    socio = socio,
                    fechaInscripcion = formatter.parse(fechaInscripcion)
                )
                clientList.add(cliente)
            }  while (cursor.moveToNext())
        }
        cursor.close()
        return clientList
    }

    fun esDeudor(cliente: Cliente): Boolean {
        val db = readableDatabase
        val query = """
        SELECT 1 FROM clientes 
        LEFT JOIN (
            SELECT IdCliente, MAX(FechaPago) AS UltimoPago
            FROM pagos
            GROUP BY IdCliente
        ) pagos ON clientes.id = pagos.IdCliente
        WHERE clientes.id = ? 
        AND socio = 1 
        AND (pagos.UltimoPago IS NULL OR pagos.UltimoPago < datetime('now', '-1 month'));
    """.trimIndent()

        val cursor = db.rawQuery(query, arrayOf(cliente.id.toString()))
        val esDeudor = cursor.moveToFirst()
        cursor.close()
        return esDeudor
    }
}