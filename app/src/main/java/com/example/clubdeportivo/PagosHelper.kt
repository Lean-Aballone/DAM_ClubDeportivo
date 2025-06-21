package com.example.clubdeportivo

import android.content.ContentValues
import android.content.Context
import com.example.clubdeportivo.entidades.Pago
import java.util.Date

class PagosHelper(context: Context): DBHelper(context) {

    fun ingresarPago(idCliente: Int, monto: Int, mes: String, idActividad: Int, formaPago: String, fechaPago: Date): Boolean{
        val db = writableDatabase
        val valores = ContentValues().apply {
            put("IdCliente", idCliente)
            put("Monto", monto)
            put("Mes", mes)
            put("IdActividad", idActividad)
            put("FormaPago", formaPago)
            put("FechaPago", fechaPago.toString())
        }

        val resultado = db.insert("pagos", null, valores)
        return resultado != -1L
    }

}