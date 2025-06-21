package com.example.clubdeportivo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class sectionAdministrar : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_section_administrar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val clientesHelper = ClientesHelper(this)

        val spinner: Spinner = findViewById(R.id.spinner)

        val listItems = listOf("DNI", "ID Socio")
        val adapter = ArrayAdapter(
            this,
            R.layout.spinner_item_closed,
            listItems
        )

        adapter.setDropDownViewResource(R.layout.spinner_item_dropdown)
        spinner.adapter = adapter




        val button = findViewById<Button>(R.id.button)
        Utils.gradientPostProcessing(button)

        button.setOnClickListener {
            val selectedType = spinner.selectedItem.toString()
            val searchValue = findViewById<EditText>(R.id.socioIdentificationField).text.toString()
            val value = searchValue.toIntOrNull() ?: 0
            if (value < 1) {
                Toast.makeText(this, "Ingrese el número de $selectedType", Toast.LENGTH_SHORT).show()
            } else {
                val dataCliente = clientesHelper.getClienteByDNIorId(selectedType, value)
                Log.v(null, dataCliente.toString())
                if (dataCliente != null) {
                    val intent = Intent(this, DetalleCliente::class.java)
                    intent.putExtra("cliente", dataCliente)
                    startActivity(intent)
                } else {
                    Toast.makeText(
                        this,
                        "No se ha encontrado ningún cliente con el $selectedType ingresado",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
    fun returnToMain(view: View){
        val intent = Intent(this, sectionMain::class.java)
        startActivity(intent)
    }

}