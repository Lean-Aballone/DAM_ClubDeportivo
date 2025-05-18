package com.example.clubdeportivo

import android.content.Intent
import android.graphics.LinearGradient
import android.graphics.Shader
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.GridView
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class actividades : AppCompatActivity() {

    data class GridItem(
        val title: String,
        val description: String,
        val imageResId: Int,
        var isChecked: Boolean = false
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_actividades)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
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
            val intent = Intent(this, sectionMain::class.java)
            startActivity(intent)
        }

        val gridView: GridView = findViewById(R.id.gridView)

        val items = listOf(
            GridItem("Fútbol", "Lunes y Jueves - 18hs a 19:30hs", R.drawable.futbol),
            GridItem("Hockey", "Martes y Viernes - 17hs a 18:30hs", R.drawable.hockey),
            GridItem("Vóley", "Miércoles - 19hs a 20hs", R.drawable.voley),
            GridItem("Básquetbol", "Lunes y Miércoles - 18hs", R.drawable.basquet),
            GridItem("Tenis", "Viernes - 17hs a 18hs", R.drawable.tenis),
            GridItem("Natación", "Sábados - 10hs a 11hs", R.drawable.natacion),
            GridItem("Tiro con Arco", "Domingos - 11hs", R.drawable.arco)
        )
        val adapter = object : ArrayAdapter<GridItem>(
            this,
            R.layout.grid_item,
            items
        ) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.grid_item, parent, false)

                val item = getItem(position)!!

                val imageView = view.findViewById<ImageView>(R.id.imageView)
                val titleView = view.findViewById<TextView>(R.id.text1)
                val descView = view.findViewById<TextView>(R.id.text2)
                val checkBox = view.findViewById<CheckBox>(R.id.checkBox)

                imageView.setImageResource(item.imageResId)
                titleView.text = item.title
                descView.text = item.description
                checkBox.isChecked = item.isChecked

                checkBox.setOnCheckedChangeListener { _, isChecked ->
                    item.isChecked = isChecked
                }

                return view
            }
        }
        gridView.adapter = adapter

        // TODO:
        // val selected = items.filter { it.isChecked }

    }
    fun returnToMain(view: View){
        val intent = Intent(this, sectionMain::class.java)
        startActivity(intent)
    }
}