package com.usiel.tezoexplorer

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ServicioAdapter(private val servicios: List<Servicio>, private val context: Context) :
    RecyclerView.Adapter<ServicioAdapter.ServicioViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServicioViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_servicio, parent, false)
        return ServicioViewHolder(view)
    }

    override fun onBindViewHolder(holder: ServicioViewHolder, position: Int) {
        val servicio = servicios[position]
        holder.bind(servicio)
    }

    override fun getItemCount(): Int = servicios.size

    inner class ServicioViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nombreTextView: TextView = itemView.findViewById(R.id.tv_servicio_nombre)
        private val descripcionTextView: TextView = itemView.findViewById(R.id.tv_servicio_descripcion)
        private val horarioTextView: TextView = itemView.findViewById(R.id.tv_servicio_horario)
        private val telefonoTextView: TextView = itemView.findViewById(R.id.tv_servicio_telefono)
        private val iconImageView: ImageView = itemView.findViewById(R.id.iv_servicio_icon)
        private val btnUbicacion: Button = itemView.findViewById(R.id.btn_ubicacion)

        fun bind(servicio: Servicio) {
            nombreTextView.text = servicio.nombre
            descripcionTextView.text = servicio.descripcion
            horarioTextView.text = servicio.horario
            telefonoTextView.text = servicio.telefono
            iconImageView.setImageResource(servicio.iconoResId)

            btnUbicacion.setOnClickListener {
                // Imprime los valores de latitud y longitud antes de enviarlos
                println("Enviando latitud: ${servicio.latitud}, longitud: ${servicio.longitud}")

                // Enviar la ubicación al abrir MapaActivity
                val intent = Intent(context, MapaActivity::class.java).apply {
                    putExtra("LATITUD_SERVICIO", servicio.latitud)
                    putExtra("LONGITUD_SERVICIO", servicio.longitud)
                }
                context.startActivity(intent)
            }
        }
    }
}
