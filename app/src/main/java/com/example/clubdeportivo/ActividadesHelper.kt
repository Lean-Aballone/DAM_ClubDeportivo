package com.example.clubdeportivo

import android.content.Context
import com.example.clubdeportivo.entidades.E_Actividades

class ActividadesHelper (context: Context): DBHelper(context) {

    fun getActividadesList(): List<E_Actividades>{
        val actividades = mutableListOf<E_Actividades>()

        val db = readableDatabase

        val query = """
            SELECT d.Tipo, a.Dias, a.HorarioInicio, a.HorarioFin
            FROM actividades a
            INNER JOIN deportes d ON a.IdDeporte = d.IdDeporte
        """.trimIndent()

        val cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                val tipoDeporte = cursor.getString(0)
                val diasString = cursor.getString(1)
                val horarioInicio = cursor.getString(2)
                val horarioFin = cursor.getString(3)

                val diasArray = diasString.split(" y ", ",", ";").map { it.trim() }.toTypedArray()

                val deporte = Deporte.valueOf(tipoDeporte.replace(' ', '_'))

                val actividad = E_Actividades(
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

}