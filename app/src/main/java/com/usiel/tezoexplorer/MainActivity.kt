package com.usiel.tezoexplorer

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore // Firestore para acceder a la base de datos

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Inicializa Firestore
        db = FirebaseFirestore.getInstance()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Referencias a las vistas en el layout
        val usuario: EditText = findViewById(R.id.edUsuario)
        val password: EditText = findViewById(R.id.edPaswword)
        val button: Button = findViewById(R.id.edInicio)
        val tvRegister: TextView = findViewById(R.id.tvRegister)

        Log.d("MainActivity", "Método onCreate ejecutado")

        // Controlamos el evento (Click) del botón de inicio de sesión
        button.setOnClickListener {
            val emailInput = usuario.text.toString().trim() // Recoge el texto de la caja de usuario
            val passwordInput = password.text.toString() // Recoge el texto de la caja de contraseña sin aplicar trim

            // Validación de campos vacíos
            if (emailInput.isEmpty() || passwordInput.isEmpty()) {
                Toast.makeText(this, "Por favor ingrese el correo y la contraseña", Toast.LENGTH_SHORT).show()
            } else if (passwordInput.contains(" ")) {
                // Verificar si la contraseña contiene espacios en blanco en cualquier parte
                Toast.makeText(this, "La contraseña no puede contener espacios", Toast.LENGTH_SHORT).show()
            } else {
                // Llamar a la función para verificar los datos de Firestore
                loginUser(emailInput, passwordInput)
            }
        }

        // Evento para el TextView "Regístrate"
        tvRegister.setOnClickListener {
            Log.d("MainActivity", "TextView 'Regístrate' fue presionado")
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    // Función para verificar usuario y contraseña desde Firestore
    private fun loginUser(emailInput: String, passwordInput: String) {
        // Referencia a la colección "usuarios"
        val usersRef = db.collection("usuarios") // Cambiado a "usuarios"

        // Consulta para encontrar un documento donde el campo "email" coincida
        usersRef.whereEqualTo("email", emailInput).get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    val document = documents.first()
                    val storedPassword = document.getString("password")

                    // Verificamos si la contraseña ingresada coincide
                    if (storedPassword == passwordInput) {
                        Toast.makeText(this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show()

                        // Después de validar, abrir la siguiente pantalla
                        val intent = Intent(this, Principal::class.java)
                        startActivity(intent)
                        finish() // Finaliza esta actividad para que no puedan regresar con "atrás"
                    } else {
                        // Si la contraseña no coincide
                        Toast.makeText(this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    // Si no se encuentra un documento con ese email
                    Toast.makeText(this, "No se encontró un usuario con ese correo", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { exception ->
                // Manejar errores al obtener los datos
                Toast.makeText(this, "Error al verificar los datos", Toast.LENGTH_SHORT).show()
                Log.d("Firestore", "Error getting documents: ", exception)
            }
    }
}
