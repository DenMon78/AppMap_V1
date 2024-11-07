package com.usiel.tezoexplorer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ServicioAdapter(private val servicios: List<Servicio>) : RecyclerView.Adapter<ServicioAdapter.ServicioViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServicioViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_servicio, parent, false)
        return ServicioViewHolder(view)
    }

    override fun onBindViewHolder(holder: ServicioViewHolder, position: Int) {
        val servicio = servicios[position]
        holder.bind(servicio)
    }

    override fun getItemCount(): Int = servicios.size

    class ServicioViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nombreTextView: TextView = itemView.findViewById(R.id.tv_servicio_nombre)
        private val descripcionTextView: TextView = itemView.findViewById(R.id.tv_servicio_descripcion)
        private val iconImageView: ImageView = itemView.findViewById(R.id.iv_servicio_icon)

        fun bind(servicio: Servicio) {
            nombreTextView.text = servicio.nombre
            descripcionTextView.text = servicio.descripcion
            iconImageView.setImageResource(servicio.iconoResId)
        }
    }
}
