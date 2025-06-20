package com.example.clubdeportivo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
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
        val userField = findViewById<EditText>(R.id.username)
        val passField = findViewById<EditText>(R.id.userPassword)
        var user: String
        var pass: String
        var rol: String

        button.setOnClickListener {
            user = userField.text.toString().trim()
            pass = passField.text.toString().trim()
            if (dbHelper.isValidUser(user, pass)) {
                Toast.makeText(
                    this,
                    "Bienvenido: " + user,
                    Toast.LENGTH_SHORT
                ).show()
                val intent = Intent(this, sectionMain::class.java)
                rol = dbHelper.getRol(user) ?: "null"
                SessionManager.currentUser = User(user, pass, rol)
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