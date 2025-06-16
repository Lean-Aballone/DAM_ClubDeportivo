package com.example.clubdeportivo

import android.content.Intent
import android.graphics.LinearGradient
import android.graphics.Shader
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.clubdeportivo.entidades.Cliente
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DetalleCliente : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detalle_cliente)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val cliente: Cliente? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra("cliente", Cliente::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getSerializableExtra("cliente") as? Cliente
        }


        cliente?.let {
            Log.d("test Cliente", "Nombre: ${it.nombre}, DNI: ${it.dni}")
        }


        val nombre = findViewById<TextView>(R.id.nombreApellido)
        val dni = findViewById<TextView>(R.id.dni)
        val fechaInscripcion = findViewById<TextView>(R.id.fechaInscripcion)
        val telefono = findViewById<TextView>(R.id.telefono)
        val direccion = findViewById<TextView>(R.id.direccion)
        val socio = findViewById<TextView>(R.id.socio)

        if (cliente != null) {
            socio.text = if(cliente.socio) { "Socio ID:" + cliente.id.toString() } else {"Cliente ID: " + cliente.id.toString()}
            nombre.text = cliente.nombre + " " + cliente.apellido
            dni.text = "DNI:" + cliente.dni.toString()
            telefono.text = "Teléfono: " + cliente.telefono
            direccion.text = "Dirección: " + cliente.direccion
            Log.d("test", cliente.fechaInscripcion.toString())
            val formato = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
            val fechaFormateada = formato.format(cliente.fechaInscripcion)
            fechaInscripcion.text = fechaFormateada
        } else {
            Toast.makeText(this, "Ha ocurrido un error. Busque el cliente por DNI o Id Socio", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, sectionAdministrar::class.java)
            startActivity(intent)
        }

        val buttons = arrayOf(
            findViewById<Button>(R.id.imprimirCarnet),
            findViewById<Button>(R.id.registarPago),
            findViewById<Button>(R.id.inscribirActividad)
        )

        findViewById<Button>(R.id.inscribirActividad).setOnClickListener {
            val intent = Intent(this, actividades::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.registarPago).setOnClickListener {
            val intent = Intent(this, registro_pago::class.java)
            startActivity(intent)
        }

    }

    fun makeButtonGraddient(button: Button){
        button.post {
            val width = button.paint.measureText(button.text.toString()) + button.paddingStart.toFloat()
            val textShader = LinearGradient(
                0f, 0f, width, 0f,
                intArrayOf(
                    0xFF00FFFF.toInt(), // #0FF
                    0xFFFF00FF.toInt() // #F0F
                ),
                null,
                Shader.TileMode.CLAMP
            )
            button.paint.shader = textShader
            button.invalidate()
        }
    }

    fun returnToMain(view: View){
        val intent = Intent(this, sectionMain::class.java)
        startActivity(intent)
    }
}