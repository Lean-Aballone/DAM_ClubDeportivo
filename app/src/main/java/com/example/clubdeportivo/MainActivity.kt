package com.example.clubdeportivo

import android.content.Intent
import android.graphics.LinearGradient
import android.graphics.Shader
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.login)
        val button = findViewById<Button>(R.id.buttonSignIn)
        button.setOnClickListener {
            val intent = Intent(this, sectionMain::class.java)
            startActivity(intent)
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        button.post {
            val width = button.width.toFloat()
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
}