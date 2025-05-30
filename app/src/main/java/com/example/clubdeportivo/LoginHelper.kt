package com.example.clubdeportivo

import android.content.Context

class LoginHelper(context: Context): DBHelper(context) {
    fun isValidUser(name: String, password: String): Boolean{
        val db = readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM usuario WHERE NombreUsu = ? AND PassUsu = ?",
            arrayOf(name, password)
        )
        val isValid = cursor.count > 0
        cursor.close()
        return isValid
    }
}