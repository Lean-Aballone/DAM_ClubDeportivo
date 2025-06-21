package com.example.clubdeportivo

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.clubdeportivo.entidades.Cliente
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.Date
import java.util.Locale

class registro_pago : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("test registro pago", "ok on create")
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registro_pago)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        var pagosHelper = PagosHelper(this)


        Log.d("test registro pago", "ok on create 2")

        val cliente = Cliente(1, "test", "aaa", 123123, "22222", "test1234", true, true, null)

        Log.d("test cliente", cliente.toString())

//        val cliente: Cliente? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//            intent.getSerializableExtra("cliente", Cliente::class.java)
//        } else {
//            @Suppress("DEPRECATION")
//            intent.getSerializableExtra("cliente") as? Cliente
//        }

        val campoFecha = findViewById<EditText>(R.id.fechaPago)
        val campoMonto = findViewById<EditText>(R.id.monto)
        val campoMes = findViewById<EditText>(R.id.mes)
        val campoEfectivo = findViewById<RadioButton>(R.id.formaPago_efectivo)


        val fechaActual = Date()
        val outputFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val formattedDate = outputFormat.format(fechaActual)
        campoFecha.setText(formattedDate)

        if (cliente != null) {
//             si es socio, mostrar mes, si no socio mostrar actividad

//             set fechaPago a hoy


        } else {
            Toast.makeText(
                this,
                "Ha ocurrido un error. Busque el cliente por DNI o Id Socio",
                Toast.LENGTH_SHORT
            ).show()
            val intent = Intent(this, sectionAdministrar::class.java)
            startActivity(intent)
        }

        val button = findViewById<Button>(R.id.button)
        Utils.gradientPostProcessing(button)

        button.setOnClickListener {

            val fechaPago = campoFecha.text.toString()
            val formatter = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
            val parsedDate: Date? = formatter.parse(fechaPago)


            var formaPago: String = if (campoEfectivo.isChecked) {"Efectivo"} else {"Tarjeta"}
            val monto = campoMonto.text.toString().toIntOrNull() ?: 0
            val mes = campoMes.text.toString().trim()
            val idCliente = cliente.id
            val idActividad = campoMonto.text.toString().toIntOrNull() ?: 0

            if (monto < 1 || parsedDate == null) {
                Toast.makeText(this, "Complete todos los datos del formulario.", Toast.LENGTH_SHORT).show()
            } else {
                if (pagosHelper.ingresarPago(idCliente,monto,mes,idActividad,formaPago,parsedDate)) {

                    Toast.makeText(this, "El pago se ha registrado correctamente", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, DetalleCliente::class.java)
                    intent.putExtra("cliente", cliente)
                    startActivity(intent)

                } else {
                    Toast.makeText(this, "El pago no se ha podido registrar.", Toast.LENGTH_SHORT).show()
                }

            }
        }

    }


    fun returnToMain(view: View) {
        val intent = Intent(this, sectionMain::class.java)
        startActivity(intent)
    }
}