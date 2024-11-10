package com.usiel.tezoexplorer

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.GridLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class Principal : AppCompatActivity() {

    private lateinit var greetingTextView: TextView
    private lateinit var searchEditText: EditText
    private lateinit var servicesGrid: GridLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal)

        // Inicializar las vistas
        greetingTextView = findViewById(R.id.text_greeting)
        searchEditText = findViewById(R.id.search_service)
        servicesGrid = findViewById(R.id.services_grid)

        // Configuración de los clics en los ítems del GridLayout
        setupServicesGrid()
    }

    private fun setupServicesGrid() {
        // Configurar los clics en cada ítem del GridLayout según su posición
        val pharmacyItem = servicesGrid.getChildAt(0)
        pharmacyItem.setOnClickListener {
            openSpecificActivity(FarmaciasActivity::class.java)
        }

        val restaurantItem = servicesGrid.getChildAt(1)
        restaurantItem.setOnClickListener {
            openSpecificActivity(RestaurantesActivity::class.java)
        }

        val bankItem = servicesGrid.getChildAt(2)
        bankItem.setOnClickListener {
            openSpecificActivity(BancosActivity::class.java)
        }

        val mechanicItem = servicesGrid.getChildAt(3)
        mechanicItem.setOnClickListener {
            openSpecificActivity(MecanicosActivity::class.java)
        }

        val hotelItem = servicesGrid.getChildAt(4)
        hotelItem.setOnClickListener {
            openSpecificActivity(HotelesActivity::class.java)
        }

        val shopItem = servicesGrid.getChildAt(5)
        shopItem.setOnClickListener {
            openSpecificActivity(TiendasActivity::class.java)
        }
    }

    private fun openSpecificActivity(activityClass: Class<*>) {
        val intent = Intent(this, activityClass)
        startActivity(intent)
    }
}
