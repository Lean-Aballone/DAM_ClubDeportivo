package com.example.clubdeportivo

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

open class DBHelper(context: Context?): SQLiteOpenHelper(context,"ClubDeportivoDB",null,2){

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

        val createTableClientes = """
            CREATE TABLE clientes (
                Id INTEGER PRIMARY KEY,
                Nombre TEXT,
                Apellido TEXT,
                DNI INTEGER UNIQUE,
                Telefono TEXT,
                Direccion TEXT,
                AptoFisico BOOLEAN,
                Socio BOOLEAN,
                FechaInscripcion DATETIME DEFAULT CURRENT_TIMESTAMP
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

        val createTableActividades = """
            CREATE TABLE actividades (
                Id INTEGER PRIMARY KEY AUTOINCREMENT,
                IdDeporte INTEGER,
                Dias TEXT,
                HorarioInicio TEXT,
                HorarioFin TEXT,
                FOREIGN KEY (IdDeporte) REFERENCES deportes(IdDeporte)
            )
        """.trimIndent()

        val createTableDeportes = """
            CREATE TABLE deportes (
                IdDeporte INTEGER PRIMARY KEY,
                Tipo TEXT
            )
        """.trimIndent()

        val insertIntoDeportes = """
            INSERT INTO deportes (IdDeporte, Tipo) VALUES
            (0, 'Futbol'),
            (1, 'Hockey'),
            (2, 'Voley'),
            (3, 'Basquetbol'),
            (4, 'Tenis'),
            (5, 'Natacion'),
            (6, 'Tiro_con_Arco');            
        """.trimIndent()

        val insertIntoActividades = """
            INSERT INTO actividades (IdDeporte, Dias, HorarioInicio, HorarioFin) VALUES
            (0,'Lunes y Jueves','18hs','19:30hs'),
            (1,'Lunes y Viernes','16hs','17:50hs'),
            (2,'Martes y Jueves','20hs','21hs'),
            (3,'Miércoles', '18hs','19hs'),
            (4,'Sábados', '18hs','19:30hs'),
            (5,'Martes y Viernes', '18hs', '19hs'),
            (6,'Sábados', '16hs', '17:30hs');
        """.trimIndent()

        val createTableClienteActividad = """
            CREATE TABLE cliente_actividad (
            IdCliente INTEGER,
            IdActividad INTEGER,
            PRIMARY KEY (IdCliente, IdActividad),
            FOREIGN KEY (IdCliente) REFERENCES clientes(Id),
            FOREIGN KEY (IdActividad) REFERENCES actividades(Id) );
        """.trimIndent()

        // Orden en el cual se ejecutarán las sentencias.
        val sql = arrayOf(createTableRoles,insertIntoRoles,createTableUsuario,insertIntoUsuario, createTableClientes, createTableActividades, createTableDeportes, insertIntoDeportes, insertIntoActividades, createTableClienteActividad)
        sql.forEach { query ->  db.execSQL(query)}
    }

    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int
    ) {
        db.execSQL("DROP TABLE IF EXISTS roles")
        db.execSQL("DROP TABLE IF EXISTS usuario")
        db.execSQL("DROP TABLE IF EXISTS clientes")
        db.execSQL("DROP TABLE IF EXISTS roles")
        db.execSQL("DROP TABLE IF EXISTS actividades")
        db.execSQL("DROP TABLE IF EXISTS cliente_actividad")
        db.execSQL("DROP TABLE IF EXISTS deportes")
        onCreate(db)
    }
}