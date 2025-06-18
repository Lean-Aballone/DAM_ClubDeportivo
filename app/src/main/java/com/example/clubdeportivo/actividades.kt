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
import android.widget.Toast
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
        Utils.gradientPostProcessing(button)

        val gridView: GridView = findViewById(R.id.gridView)

        val dbHelper = ActividadesHelper(this)
        val actividadesList = dbHelper.getActividadesList()

        val items = actividadesList.map {
            val nombre = it.deporte.name.replace('_', ' ')
            val dias = it.dias.joinToString(" y ")
            val description = "$dias - ${it.HorarioInicio} a ${it.HorarioFin}"
            val imageResId = when (it.deporte) {
                Deporte.Futbol -> R.drawable.futbol
                Deporte.Hockey -> R.drawable.hockey
                Deporte.Voley -> R.drawable.voley
                Deporte.Basquetbol -> R.drawable.basquet
                Deporte.Tenis -> R.drawable.tenis
                Deporte.Natacion -> R.drawable.natacion
                Deporte.Tiro_con_Arco -> R.drawable.arco
            }
            GridItem(nombre, description, imageResId)
        }

        button.setOnClickListener {
            val selectedIds = actividadesList
                .filterIndexed { index, _ -> items[index].isChecked }
                .map { it.id }

            val idCliente = intent.getIntExtra("IdCliente", -1)
            if (idCliente != -1) {
                dbHelper.inscribirClienteEnActividades(idCliente, selectedIds)
                Toast.makeText(this, "Actividades guardadas", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, sectionMain::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Error: cliente no identificado", Toast.LENGTH_SHORT).show()
            }
        }

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

    }

    fun returnToMain(view: View){
        val intent = Intent(this, sectionMain::class.java)
        startActivity(intent)
    }
}