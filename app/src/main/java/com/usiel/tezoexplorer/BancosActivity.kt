package com.usiel.tezoexplorer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class BancosActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bancos)

        val listaDeBancos = obtenerListaDeBancos()

        val recyclerView = findViewById<RecyclerView>(R.id.banco_recycler)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = ServicioAdapter(listaDeBancos, this) // Pasamos el contexto como segundo parámetro
    }

    private fun obtenerListaDeBancos(): List<Servicio> {
        return listOf(
            Servicio(
                nombre = "Banco 1",
                descripcion = "",
                horario = "9:00 - 16:00",
                telefono = "123-456-7890",
                latitud = 20.1930883,
                longitud = -99.2719234,
                iconoResId = R.drawable.bancos
            ),
            Servicio(
                nombre = "Banco 2",
                descripcion = "",
                horario = "9:00 - 16:00",
                telefono = "987-654-3210",
                latitud =  20.1925483,
                longitud = -99.2716331,
                iconoResId = R.drawable.farmacia
            )
            // Agrega más bancos si es necesario
        )
    }
}
