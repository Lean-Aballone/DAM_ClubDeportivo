package com.example.clubdeportivo

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.clubdeportivo.entidades.User
import com.google.android.material.navigation.NavigationView

class sectionMain : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_section_main)
        val usuario = intent.getSerializableExtra("user") as? User
        val userDetails = findViewById<TextView>(R.id.userDetails)
        userDetails.text = usuario?.nombre.toString() + " (" + usuario?.rol.toString() + ")"
        drawerLayout = findViewById(R.id.main)
        val btnOpenDrawer: ImageButton = findViewById(R.id.btn_open_drawer)

        btnOpenDrawer.setOnClickListener {
            if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
                drawerLayout.closeDrawer(GravityCompat.END)
            } else {
                drawerLayout.openDrawer(GravityCompat.END)
            }
        }
        val navView: NavigationView = findViewById(R.id.nav_view)
        navView.setNavigationItemSelectedListener {
            drawerLayout.closeDrawer(GravityCompat.END)
            true
        }

    }
    fun closeSession(view: View){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
    fun closeDrawer(view: View){
        drawerLayout.closeDrawer(GravityCompat.END)
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
        val intent = Intent(this, sectionActividad::class.java)
        startActivity(intent)
    }
    fun launchCuotasVencidas(view: View){
        val intent = Intent(this, cuotaVencida::class.java)
        startActivity(intent)
    }
}