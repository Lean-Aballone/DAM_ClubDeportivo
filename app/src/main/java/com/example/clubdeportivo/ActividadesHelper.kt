package com.example.clubdeportivo

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.clubdeportivo.entidades.E_Actividades

class ActividadesHelper (context: Context): DBHelper(context) {

    fun getActividadesList(): List<E_Actividades>{
        val actividades = mutableListOf<E_Actividades>()
        val db = readableDatabase

        val query = """
            SELECT a.Id, d.Tipo, a.Dias, a.HorarioInicio, a.HorarioFin
            FROM actividades a
            INNER JOIN deportes d ON a.IdDeporte = d.IdDeporte
        """.trimIndent()

        val cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(0)
                val tipoDeporte = cursor.getString(1)
                val diasString = cursor.getString(2)
                val horarioInicio = cursor.getString(3)
                val horarioFin = cursor.getString(4)

                val diasArray = diasString.split(" y ", ",", ";").map { it.trim() }.toTypedArray()
                val deporte = Deporte.valueOf(tipoDeporte.replace(' ', '_'))

                val actividad = E_Actividades(
                    id = id,
                    deporte = deporte,
                    dias = diasArray,
                    HorarioInicio = horarioInicio,
                    HorarioFin = horarioFin
                )

                actividades.add(actividad)
            } while (cursor.moveToNext())
        }

        cursor.close()
        return actividades
    }

    fun inscribirClienteEnActividades(idCliente: Int, idsActividades: List<Int>) {
        val db = writableDatabase
        idsActividades.forEach { idActividad ->
            val values = ContentValues().apply {
                put("IdCliente", idCliente)
                put("IdActividad", idActividad)
            }
            db.insertWithOnConflict(
                "cliente_actividad",
                null,
                values,
                SQLiteDatabase.CONFLICT_IGNORE // evita error si ya estaba inscripto
            )
        }
    }

    fun getActividadesDeCliente(idCliente: Int): List<E_Actividades> {
        val db = readableDatabase
        val actividades = mutableListOf<E_Actividades>()

        val query = """
            SELECT d.Tipo, a.Dias, a.HorarioInicio, a.HorarioFin
            FROM actividades a
            INNER JOIN deportes d ON a.IdDeporte = d.IdDeporte
            INNER JOIN cliente_actividad ca ON ca.IdActividad = a.Id
            WHERE ca.IdCliente = ?
        """.trimIndent()

        val cursor = db.rawQuery(query, arrayOf(idCliente.toString()))

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(0)
                val tipoDeporte = cursor.getString(1)
                val diasArray = cursor.getString(2).split(" y ", ",", ";").map { it.trim() }.toTypedArray()
                val horarioInicio = cursor.getString(3)
                val horarioFin = cursor.getString(4)

                val deporte = Deporte.valueOf(tipoDeporte.replace(' ', '_'))

                actividades.add(
                    E_Actividades(id, deporte, diasArray, horarioInicio, horarioFin)
                )
            } while (cursor.moveToNext())
        }

        cursor.close()
        return actividades
    }

    fun getIdsActividadesDeCliente(idCliente: Int): List<Int> {
        val db = readableDatabase
        val lista = mutableListOf<Int>()

        val query = "SELECT IdActividad FROM cliente_actividad WHERE IdCliente = ?"
        val cursor = db.rawQuery(query, arrayOf(idCliente.toString()))

        if (cursor.moveToFirst()) {
            do {
                lista.add(cursor.getInt(0))
            } while (cursor.moveToNext())
        }

        cursor.close()
        return lista
    }

    fun updateInscripcionesCliente(idCliente: Int, nuevasIds: List<Int>) {
        val db = writableDatabase

        db.delete("cliente_actividad", "IdCliente = ?", arrayOf(idCliente.toString()))

        inscribirClienteEnActividades(idCliente, nuevasIds)
    }

}