package com.example.clubdeportivo

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.example.clubdeportivo.entidades.Cliente
import java.text.SimpleDateFormat
import java.util.Locale

class ClienteAdapter(private val context: Context, private val clientes: List<Cliente>) : BaseAdapter() {

    override fun getCount(): Int = clientes.size
    override fun getItem(position: Int): Any = clientes[position]
    override fun getItemId(position: Int): Long = clientes[position].id.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_cliente, parent, false)

        val cliente = clientes[position]

        val nombreApellido = view.findViewById<TextView>(R.id.nombreApellido)
        val dni = view.findViewById<TextView>(R.id.dni)
        val telefono = view.findViewById<TextView>(R.id.telefono)
        val direccion = view.findViewById<TextView>(R.id.direccion)
        val aptoFisico = view.findViewById<TextView>(R.id.aptoFisico)
        val fechaInscripcion = view.findViewById<TextView>(R.id.fechaInscripcion)
        val extraInfoLayout = view.findViewById<LinearLayout>(R.id.extraInfoLayout)
        val btnAcciones = view.findViewById<ImageButton>(R.id.btnAcciones)


        val outputFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val formattedDate = outputFormat.format(cliente.fechaInscripcion)

        nombreApellido.text = "${cliente.nombre} ${cliente.apellido}"
        dni.text = "DNI: ${cliente.dni}"
        telefono.text = "Tel: ${cliente.telefono}"
        direccion.text = "Dirección: ${cliente.direccion}"
        aptoFisico.text = if (cliente.aptoFisico) "Apto físico: Sí" else "Apto físico: No"
        fechaInscripcion.text = "Inscripción: ${formattedDate}"

        view.setOnClickListener {
            extraInfoLayout.visibility = if (extraInfoLayout.visibility == View.VISIBLE) View.GONE else View.VISIBLE
        }

        btnAcciones.setOnClickListener {
            // TODO: Redirigir a Pago cuota
            Toast.makeText(context, "Acciones para ${cliente.nombre}", Toast.LENGTH_SHORT).show()
//            val intent = Intent(this, registro_pago::class.java)
//            intent.putExtra("cliente", cliente)
//            startActivity(intent)
        }

        return view
    }
}
