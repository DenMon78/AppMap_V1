package com.usiel.tezoexplorer


import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.GridLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class Principal : AppCompatActivity() {

    private lateinit var greetingTextView: TextView
    private lateinit var searchEditText: EditText
    private lateinit var servicesGrid: GridLayout // Declaración de servicesGrid

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal) // Asegúrate de que el nombre del layout sea correcto

        // Inicializar las vistas
        greetingTextView = findViewById(R.id.text_greeting)
        searchEditText = findViewById(R.id.search_service)
        servicesGrid = findViewById(R.id.services_grid) // Inicializa el GridLayout

        // Configuración de los clics en los ítems del GridLayout
        setupServicesGrid()
    }

    private fun setupServicesGrid() {
        // Configurar los clics en cada ítem del GridLayout según su posición
        val pharmacyItem = servicesGrid.getChildAt(0) // Ítem de Farmacias
        pharmacyItem.setOnClickListener {
            openDetailActivity("Farmacias") // Abre una nueva actividad para Farmacias
        }

        val restaurantItem = servicesGrid.getChildAt(1) // Ítem de Restaurantes
        restaurantItem.setOnClickListener {
            openDetailActivity("Restaurantes") // Abre una nueva actividad para Restaurantes
        }

        val bankItem = servicesGrid.getChildAt(2) // Ítem de Bancos
        bankItem.setOnClickListener {
            openDetailActivity("Bancos") // Abre una nueva actividad para Bancos
        }

        val mechanicItem = servicesGrid.getChildAt(3) // Ítem de Mecánicos
        mechanicItem.setOnClickListener {
            openDetailActivity("Mecánicos") // Abre una nueva actividad para Mecánicos
        }

        val hotelItem = servicesGrid.getChildAt(4) // Ítem de Hoteles
        hotelItem.setOnClickListener {
            openDetailActivity("Hoteles") // Abre una nueva actividad para Hoteles
        }

        val shopItem = servicesGrid.getChildAt(5) // Ítem de Tiendas
        shopItem.setOnClickListener {
            openDetailActivity("Tiendas") // Abre una nueva actividad para Tiendas
        }
    }

    private fun openDetailActivity(serviceName: String) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("SERVICE_NAME", serviceName) // Envía el nombre del servicio
        startActivity(intent)
    }
}
