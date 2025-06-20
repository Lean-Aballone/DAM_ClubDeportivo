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
    fun getRol(user: String): String? {
        val db = readableDatabase
        val query = """
        SELECT r.NomRol
        FROM usuario u
        JOIN roles r ON u.RolUsu = r.RolUsu
        WHERE u.NombreUsu = ?
    """.trimIndent()

        val cursor = db.rawQuery(query, arrayOf(user))

        return if (cursor.moveToFirst()) {
            val rol = cursor.getString(0)
            cursor.close()
            rol
        } else {
            cursor.close()
            null
        }
    }
}