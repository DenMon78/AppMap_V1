package com.usiel.tezoexplorer

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DetailActivity : AppCompatActivity() {

    private lateinit var serviceTitleTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail) // Verifica que este layout exista

        // Inicializar el TextView
        serviceTitleTextView = findViewById(R.id.service_title)

        // Obtener el nombre del servicio desde el Intent
        val serviceName = intent.getStringExtra("SERVICE_NAME")

        // Mostrar el nombre del servicio en el TextView
        serviceTitleTextView.text = serviceName
    }
}
