package com.example.clubdeportivo

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.clubdeportivo.entidades.Cliente
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class registro_pago : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registro_pago)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val pagosHelper = PagosHelper(this)
        val actividadesHelper = ActividadesHelper(this)

        val cliente: Cliente? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra("cliente", Cliente::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getSerializableExtra("cliente") as? Cliente
        }

        val button = findViewById<Button>(R.id.button)
        Utils.gradientPostProcessing(button)

        val spinnerMes: Spinner = findViewById(R.id.spinnerMes)
        val listMeses = listOf("Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre")

        val spinnerActividad: Spinner = findViewById(R.id.spinnerActividad)
        val actividades = actividadesHelper.getActividadesList()

        val campoFecha = findViewById<EditText>(R.id.fechaPago)
        val campoMonto = findViewById<EditText>(R.id.monto)
        val campoEfectivo = findViewById<RadioButton>(R.id.formaPago_efectivo)

        val fechaActual = Date()
        val outputFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val formattedDate = outputFormat.format(fechaActual)
        campoFecha.setText(formattedDate)

        val formLayoutMes = findViewById<LinearLayout>(R.id.formLayoutMes)
        val formLayoutActividad = findViewById<LinearLayout>(R.id.formLayoutActividad)

        if (cliente != null) {
//             si es socio, mostrar mes, si no socio mostrar actividad
            if (cliente.socio) {
                formLayoutMes.visibility = View.VISIBLE

                val adapter = ArrayAdapter(this,R.layout.spinner_item_closed, listMeses)
                adapter.setDropDownViewResource(R.layout.spinner_item_dropdown)
                spinnerMes.adapter = adapter

            } else {
                formLayoutActividad.visibility = View.VISIBLE

                val adapter = ArrayAdapter(this,R.layout.spinner_item_closed, actividades.map{it.deporte.name.replace('_', ' ')})
                adapter.setDropDownViewResource(R.layout.spinner_item_dropdown)
                spinnerActividad.adapter = adapter
            }


            button.setOnClickListener {
                val fechaPago = campoFecha.text.toString()

                val formaPago: String = if (campoEfectivo.isChecked) {"Efectivo"} else {"Tarjeta"}
                val monto = campoMonto.text.toString().toIntOrNull() ?: 0
                val idCliente = cliente.id
                val mes: String
                val idActividad: Int

                if (!cliente.socio) {
                    val selectedActividad = actividades[spinnerActividad.selectedItemPosition]
                    idActividad = selectedActividad.id
                    mes = ""
                } else {
                    idActividad = 0
                    mes = spinnerMes.selectedItem.toString()
                }

                if (monto < 1 || fechaPago == null) {
                    Toast.makeText(this, "Complete todos los datos del formulario. Ingrese un monto y una fecha valida.", Toast.LENGTH_SHORT).show()
                } else {
                    val inputFormat = SimpleDateFormat("ydd-MM-yyyy", Locale.getDefault())
                    val fecha: Date = inputFormat.parse(fechaPago)!!

                    if (pagosHelper.ingresarPago(idCliente,monto,mes,idActividad,formaPago,fecha)) {
                        Toast.makeText(this, "El pago se ha registrado correctamente.", Toast.LENGTH_SHORT).show()

                        val intent = Intent(this, DetalleCliente::class.java)
                        intent.putExtra("cliente", cliente)
                        startActivity(intent)

                    } else {
                        Toast.makeText(this, "El pago no se ha podido registrar.", Toast.LENGTH_SHORT).show()
                    }

                }
            }

        } else {
            Toast.makeText(
                this,
                "Ha ocurrido un error. Busque el cliente por DNI o Id Socio",
                Toast.LENGTH_SHORT
            ).show()
            val intent = Intent(this, sectionAdministrar::class.java)
            startActivity(intent)
        }

    }


    fun returnToMain(view: View) {
        val intent = Intent(this, sectionMain::class.java)
        startActivity(intent)
    }
}