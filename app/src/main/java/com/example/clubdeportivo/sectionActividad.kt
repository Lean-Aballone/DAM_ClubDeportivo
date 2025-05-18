package com.example.clubdeportivo

import android.content.Intent
import android.graphics.LinearGradient
import android.graphics.Shader
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
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
        val listItems = listOf("DNI", "test")
        val adapter = ArrayAdapter(
            this,
            R.layout.spinner_item_closed,
            listItems
        )


        adapter.setDropDownViewResource(R.layout.spinner_item_dropdown)
        spinner.adapter = adapter
        val button = findViewById<Button>(R.id.button)
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
        button.setOnClickListener {
            //val intent = Intent(this, actividades::class.java)
            //startActivity(intent)
        }
    }
    fun returnToMain(view: View){
        val intent = Intent(this, sectionMain::class.java)
        startActivity(intent)
    }

}