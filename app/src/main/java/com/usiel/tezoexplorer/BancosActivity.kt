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

        // Cambié el ID de rv_banco a banco_recycler para coincidir con el XML actualizado
        val recyclerView = findViewById<RecyclerView>(R.id.banco_recycler)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = ServicioAdapter(listaDeBancos)
    }

    private fun obtenerListaDeBancos(): List<Servicio> {
        return listOf(
            Servicio("Banco 1", "Dirección del Banco 1", R.drawable.bancos),
            Servicio("Banco 2", "Dirección del Banco 2", R.drawable.farmacia),
            // Agrega más bancos si es necesario
        )
    }
}
