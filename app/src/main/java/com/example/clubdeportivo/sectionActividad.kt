package com.example.clubdeportivo

import android.content.Intent
import android.graphics.LinearGradient
import android.graphics.Shader
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class sectionActividad : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_section_actividad)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val spinner: Spinner = findViewById(R.id.spinner)
        val listItems = listOf("DNI", "ID")
        val adapter = ArrayAdapter(
            this,
            R.layout.spinner_item_closed,
            listItems
        )

        adapter.setDropDownViewResource(R.layout.spinner_item_dropdown)
        spinner.adapter = adapter
        val button = findViewById<Button>(R.id.button)
        Utils.gradientPostProcessing(button)
        val infoSocio = findViewById<EditText>(R.id.socioIdentificationField)
        val dbHelper = ClientesHelper(this)
        button.setOnClickListener {
            val intent = Intent(this, actividades::class.java)
            when(spinner.selectedItemId){
                //DNI
                0L -> intent.putExtra("DNI", infoSocio.getText().toString())
                //ID converted to DNI
                1L -> intent.putExtra("DNI", dbHelper.getClienteByDNIorId("ID",infoSocio.getText().toString().toInt()).dni.toString())
            }
            startActivity(intent)
        }
    }
    fun returnToMain(view: View){
        val intent = Intent(this, sectionMain::class.java)
        startActivity(intent)
    }

}