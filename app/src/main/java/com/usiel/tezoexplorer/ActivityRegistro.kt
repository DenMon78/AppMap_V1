package com.usiel.tezoexplorer

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class RegisterActivity : AppCompatActivity() {

    // Inicializar la instancia de Firestore
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        // Referencias a los elementos del diseño (Email, Contraseña, Confirmar Contraseña y Botón)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val etConfirmPassword = findViewById<EditText>(R.id.etConfirmPassword)
        val btnRegister = findViewById<Button>(R.id.btnRegister)

        // Referencia al nuevo botón de "Volver a Iniciar Sesión"
        val btnBackToLogin = findViewById<Button>(R.id.btnBackToLogin)

        // Expresión regular para validar contraseña con al menos 1 carácter especial
        val passwordPattern = "^(?=.*[!@#\$%^&*(),.?\":{}|<>]).{8,}\$".toRegex()

        // Configurar el comportamiento del botón de registro
        btnRegister.setOnClickListener {
            // Obtener los valores de los campos de correo, contraseña y confirmación de contraseña
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString() // No quitar espacios aún
            val confirmPassword = etConfirmPassword.text.toString() // No quitar espacios aún

            // Verificar que todos los campos no estén vacíos
            if (email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()) {

                // Validar formato de correo electrónico
                if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

                    // Validar que la contraseña tenga al menos 8 caracteres y al menos un carácter especial
                    if (passwordPattern.matches(password) && password == confirmPassword) {

                        // Verificar si hay espacios en la contraseña
                        if (password.contains(" ")) {
                            Toast.makeText(this, "La contraseña no debe contener espacios", Toast.LENGTH_SHORT).show()
                            return@setOnClickListener
                        }

                        // Verificar si el correo ya existe en Firestore antes de registrar
                        db.collection("usuarios").whereEqualTo("email", email).get()
                            .addOnSuccessListener { documents ->
                                if (!documents.isEmpty) {
                                    // Si el correo ya existe, mostrar un mensaje y dejar al usuario en la pantalla de registro
                                    Toast.makeText(this, "Este correo ya está registrado", Toast.LENGTH_SHORT).show()
                                } else {
                                    // Si no existe, registrar el nuevo usuario
                                    val user = hashMapOf(
                                        "email" to email,
                                        "password" to password // Nota: deberías hashear la contraseña en una aplicación real
                                    )

                                    // Añadir un nuevo documento con un ID automático
                                    db.collection("usuarios")
                                        .add(user)
                                        .addOnSuccessListener {
                                            Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()

                                            // Redirigir a la pantalla de inicio de sesión después del registro exitoso
                                            val intent = Intent(this, MainActivity::class.java)
                                            startActivity(intent)
                                            finish()
                                        }
                                        .addOnFailureListener { e ->
                                            Toast.makeText(this, "Error al registrar usuario: ${e.message}", Toast.LENGTH_SHORT).show()
                                        }
                                }
                            }
                            .addOnFailureListener { e ->
                                Toast.makeText(this, "Error al verificar el correo: ${e.message}", Toast.LENGTH_SHORT).show()
                            }

                    } else if (password != confirmPassword) {
                        // Mostrar mensaje de error si las contraseñas no coinciden
                        Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                    } else {
                        // Mostrar mensaje si la contraseña no cumple con los requisitos
                        Toast.makeText(this, "La contraseña debe tener al menos 8 caracteres y un carácter especial", Toast.LENGTH_SHORT).show()
                    }

                } else {
                    // Mostrar mensaje si el formato de correo es inválido
                    Toast.makeText(this, "Por favor, ingresa un correo válido", Toast.LENGTH_SHORT).show()
                }
            } else {
                // Mostrar mensaje si hay algún campo vacío
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

        // Configurar el comportamiento del botón "Volver a Iniciar Sesión"
        btnBackToLogin.setOnClickListener {
            // Abre la actividad de inicio de sesión
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()  // Finaliza la actividad actual para que no regrese aquí cuando el usuario presione "atrás"
        }
    }
}
