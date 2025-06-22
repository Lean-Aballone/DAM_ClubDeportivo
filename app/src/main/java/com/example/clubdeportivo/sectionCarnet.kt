package com.example.clubdeportivo

import android.graphics.pdf.PdfDocument
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.clubdeportivo.entidades.Cliente
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class sectionCarnet : AppCompatActivity() {

    private lateinit var cliente: Cliente

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_carnet)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.scrollViewCarnet)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        cliente = intent.getSerializableExtra("cliente") as Cliente

        val tvTitulo = findViewById<TextView>(R.id.tvTitulo)
        val tvNombre = findViewById<TextView>(R.id.tvNombre)
        val tvDni = findViewById<TextView>(R.id.tvDni)
        val tvTelefono = findViewById<TextView>(R.id.tvTelefono)
        val tvDireccion = findViewById<TextView>(R.id.tvDireccion)
        val tvFecha = findViewById<TextView>(R.id.tvFecha)
        val tvApto = findViewById<TextView>(R.id.tvApto)
        val btnGuardarPdf = findViewById<Button>(R.id.btnGuardarPdf)
        val textComponents = arrayOf(tvTitulo,tvNombre,tvDni,tvTelefono,tvDireccion,tvFecha,tvApto)

        tvNombre.text = "Nombre: ${cliente.nombre} ${cliente.apellido}"
        tvDni.text = "DNI: ${cliente.dni}"
        tvTelefono.text = "Teléfono: ${cliente.telefono}"
        tvDireccion.text = "Dirección: ${cliente.direccion}"
        tvFecha.text = "Inscripción: ${cliente.fechaInscripcion?.toString() ?: "No disponible"}"
        tvApto.text = "Apto físico: ${if (cliente.aptoFisico) "Sí" else "No"}"

        btnGuardarPdf.setOnClickListener {
            generarPdf(textComponents)
            finish()
            startActivity(getIntent())
        }
    }
    private fun generarPdf(textComponents: Array<TextView>) {
        val carnetLayout = findViewById<LinearLayout>(R.id.carnetLayout)
        val btnGuardarPdf = findViewById<Button>(R.id.btnGuardarPdf)
        textComponents.forEach { it.setTextColor(R.color.black.toInt()) }
        btnGuardarPdf.visibility = View.GONE
        carnetLayout.post {
            val width = carnetLayout.width
            val height = carnetLayout.height

            if (width == 0 || height == 0) {
                Toast.makeText(this, "Error: el layout no está listo aún", Toast.LENGTH_SHORT).show()
                return@post
            }

            val pdfDocument = PdfDocument()
            val pageInfo = PdfDocument.PageInfo.Builder(width, height, 1).create()
            val page = pdfDocument.startPage(pageInfo)

            carnetLayout.draw(page.canvas)
            pdfDocument.finishPage(page)

            val fileName = "Carnet_${cliente.dni}.pdf"
            val file = File(getExternalFilesDir(null), fileName)

            try {
                pdfDocument.writeTo(FileOutputStream(file))
                Toast.makeText(this, "PDF guardado: ${file.absolutePath}", Toast.LENGTH_LONG).show()
            } catch (e: IOException) {
                Toast.makeText(this, "Error al guardar PDF: ${e.message}", Toast.LENGTH_LONG).show()
            } finally {
                pdfDocument.close()
                btnGuardarPdf.visibility = View.VISIBLE
                textComponents.forEach { it.setTextColor(R.color.white.toInt()) }
            }
        }
    }
}