package com.example.clubdeportivo

import android.content.Intent
import android.graphics.LinearGradient
import android.graphics.Shader
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.clubdeportivo.entidades.User

class MainActivity : AppCompatActivity() {

    lateinit var dbHelper: LoginHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.login)
        dbHelper = LoginHelper(this)
        val button = findViewById<Button>(R.id.buttonSignIn)
        val user = findViewById<EditText>(R.id.editTextText)
        val pass = findViewById<EditText>(R.id.editTextTextPassword)

        button.setOnClickListener {
            if (dbHelper.isValidUser(user.text.toString().trim(), pass.text.toString().trim())) {
                Toast.makeText(
                    this,
                    "Bienvenido: " + user.text.toString().trim(),
                    Toast.LENGTH_SHORT
                ).show()
                val intent = Intent(this, sectionMain::class.java)
                intent.putExtra("user", User("Usuario_a", "a", "Administrador"))
                startActivity(intent)
            } else {
                Toast.makeText(this, "com.example.clubdeportivo.Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        Utils.gradientPostProcessing(button)
    }
}