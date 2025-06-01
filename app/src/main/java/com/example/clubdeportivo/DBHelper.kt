package com.example.clubdeportivo

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

open class DBHelper(context: Context?): SQLiteOpenHelper(context,"ClubDeportivoDB",null,1){

    override fun onCreate(db: SQLiteDatabase) {
        val createTableRoles = """
            CREATE TABLE roles (
                RolUsu INTEGER PRIMARY KEY,
                NomRol TEXT
            );
        """.trimIndent()

        val createTableUsuario = """
            CREATE TABLE usuario (
                CodUsu INTEGER PRIMARY KEY AUTOINCREMENT,
                NombreUsu TEXT,
                PassUsu TEXT,
                RolUsu INTEGER,
                Activo INTEGER DEFAULT 1,
                FOREIGN KEY (RolUsu) REFERENCES roles(RolUsu)
            );
        """.trimIndent()

        val insertIntoRoles = """
            INSERT INTO roles (RolUsu, NomRol) VALUES
            (99, 'Administrador'),
            (100, 'Recepcionista'),
            (101, 'Profesor');
        """.trimIndent()

        val insertIntoUsuario = """
            INSERT INTO usuario (NombreUsu, PassUsu, RolUsu) VALUES
            ('Usuario_a', 'a', 99),
            ('Usuario_b', 'b', 100),
            ('Usuario_c', 'c', 101);
        """.trimIndent()

        // Orden en el cual se ejecutarán las sentencias.
        val sql = arrayOf(createTableRoles,insertIntoRoles,createTableUsuario,insertIntoUsuario)
        sql.forEach { query ->  db.execSQL(query)}
    }

    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int
    ) {
        db.execSQL("DROP TABLE IF EXISTS roles")
        db.execSQL("DROP TABLE IF EXISTS usuario")
        onCreate(db)
    }
}