package com.example.tuticket

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase


class RegistroActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        val btnRegistrar = findViewById<Button>(R.id.btnRegistrar)
        val btnCuentaExistente = findViewById<Button>(R.id.btnCuentaExistente)
        val etNombre = findViewById<EditText>(R.id.etNombre)
        val etApellido = findViewById<EditText>(R.id.etApellido)
        val etNumero = findViewById<EditText>(R.id.etNumero)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)

        btnRegistrar.setOnClickListener{
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()
            val nombres = etNombre.text.toString()
            val apellidos = etApellido.text.toString()
            val numeroTel = etNumero.text.toString()

            if (email.isEmpty() || password.isEmpty() || nombres.isEmpty() || apellidos.isEmpty()|| numeroTel.isEmpty()){
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
            } else{
                registrarUsuario(email, password, nombres, apellidos, numeroTel)
            }
        }

        btnCuentaExistente.setOnClickListener(){
            val intent = Intent(this, InicioSesion::class.java)
            startActivity(intent)
        }

    }

    private fun registrarUsuario(email: String, password: String, nombres: String, apellidos: String, numeroTel: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    Toast.makeText(this, "Registro exitoso: ${user?.email}", Toast.LENGTH_SHORT).show()


                    guardarRegistroEnDatabase(user?.uid ?: "", email, nombres, apellidos, numeroTel)

                    val intent = Intent(this, InicioSesion::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Error al registrar: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun guardarRegistroEnDatabase(userId: String, email: String, nombres: String, apellidos: String, numeroTel: String) {
        val databaseReference = database.reference.child("registros").child(userId)
        val userData = HashMap<String, Any>()
        // Guardar datos
        userData["email"] = email
        userData["nombres"] = nombres
        userData["apellidos"] = apellidos
        userData["numeroTel"] = numeroTel

        databaseReference.setValue(userData)
            .addOnSuccessListener {
                // Guardado exitoso
                Toast.makeText(this, "Registro guardado en la base de datos", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                // Fallo al guardar
                Toast.makeText(this, "Error al guardar en la base de datos: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }


}
