package com.example.clubdeportivo

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class sectionMain : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_section_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    fun launchInscribirCliente(view: View){
        val intent = Intent(this, sectionInscripcion::class.java)
        startActivity(intent)
    }
    fun launchAdministrarCliente(view: View){
        val intent = Intent(this, sectionAdministrar::class.java)
        startActivity(intent)
    }
    fun launchInscribirActividad(view: View){
        val intent = Intent(this, sectionActividades::class.java)
        startActivity(intent)
    }
    fun launchCuotasVencidas(view: View){
        val intent = Intent(this, sectionCuotas::class.java)
        startActivity(intent)
    }
}