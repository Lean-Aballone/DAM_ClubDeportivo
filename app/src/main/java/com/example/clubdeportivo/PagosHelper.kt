package com.example.clubdeportivo

import android.content.ContentValues
import android.content.Context
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PagosHelper(context: Context): DBHelper(context) {

    fun ingresarPago(idCliente: Int, monto: Int, mes: String, idActividad: Int, formaPago: String, fechaPago: Date): Boolean{

        val outputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val formattedDate: String = outputFormat.format(fechaPago)

        val db = writableDatabase
        val valores = ContentValues().apply {
            put("IdCliente", idCliente)
            put("Monto", monto)
            put("Mes", mes)
            put("IdActividad", idActividad)
            put("FormaPago", formaPago)
            put("FechaPago", formattedDate)
        }

        val resultado = db.insert("pagos", null, valores)
        return resultado != -1L
    }

}