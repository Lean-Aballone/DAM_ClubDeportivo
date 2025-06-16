package com.example.clubdeportivo

import android.content.Intent
import android.graphics.LinearGradient
import android.graphics.Shader
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SwitchCompat
import androidx.core.view.WindowInsetsCompat

class sectionInscripcion : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_section_inscripcion)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        var clientesHelper = ClientesHelper(this)

        val editNombre = findViewById<EditText>(R.id.editTextNombre)
        val editApellido = findViewById<EditText>(R.id.editTextApellido)
        val editDNI = findViewById<EditText>(R.id.editTextDNI)
        val editTelefono = findViewById<EditText>(R.id.editTextTelefono)
        val editDireccion = findViewById<EditText>(R.id.editTextDireccion)
        val editAptoFisico = findViewById<SwitchCompat>(R.id.switchCompatAptoFisico)
        val editSocio = findViewById<SwitchCompat>(R.id.switchCompatSocio)


        val aptoFisicoPresentado = findViewById<TextView>(R.id.AptoFisicoPresentado)
        editAptoFisico.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                aptoFisicoPresentado.text = "Presentado"
            } else {
                aptoFisicoPresentado.text = "No Presentado"
            }
        }

        val textViewNoSocio = findViewById<TextView>(R.id.textViewNoSocio)
        editSocio.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                textViewNoSocio.text = "Socio"
            } else {
                textViewNoSocio.text = "No Socio"
            }
        }


        val button = findViewById<Button>(R.id.button)
        Utils.gradientPostProcessing(button)
        
        button.setOnClickListener {

            val nombre = editNombre.text.toString().trim()
            val apellido = editApellido.text.toString().trim()
            val dni = editDNI.text.toString().toIntOrNull() ?: 0
            val telefono = editTelefono.text.toString().trim()
            val direccion = editDireccion.text.toString().trim()
            val aptoFisico = editAptoFisico.isChecked()
            val socio = editSocio.isChecked()

            if (!editAptoFisico.isChecked) {
                Toast.makeText(this, "Para registrar un nuevo cliente, el mismo debe presentar el apto fisico.", Toast.LENGTH_SHORT).show()
            } else if (dni < 99999 || nombre.isNullOrBlank()  || apellido.isNullOrBlank()  || telefono.isNullOrBlank() || direccion.isNullOrBlank() ) {
                Toast.makeText(this, "Complete todos los datos del formulario.", Toast.LENGTH_SHORT).show()
            } else {
                if (clientesHelper.inscribirCliente(nombre, apellido, dni, telefono, direccion, aptoFisico, socio)) {
                    val dataCliente = clientesHelper.getClienteByDNIorId("DNI", dni)
                    if (dataCliente != null) {
                        Toast.makeText(
                            this, "El cliente se ha registrado correctamente", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, DetalleCliente::class.java)
                        intent.putExtra("cliente", dataCliente)
                        startActivity(intent)
                    } else {
                        Toast.makeText(
                            this,"Ha ocurrido un error. Vuelva a intentarlo",Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this,"El cliente no se ha podido registrar.",Toast.LENGTH_SHORT).show()
                }

            }
        }
        
    }

    fun returnToMain(view: View){
        val intent = Intent(this, sectionMain::class.java)
        startActivity(intent)
    }
}