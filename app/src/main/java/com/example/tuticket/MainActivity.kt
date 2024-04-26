package com.example.tuticket

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Acceder a Firestore
        val db = FirebaseFirestore.getInstance()

        // Array de ImageViews
        val imageViews = arrayOf(
            findViewById<ImageView>(R.id.imageView8),
            findViewById<ImageView>(R.id.imageView9),
            findViewById<ImageView>(R.id.imageView10),
            findViewById<ImageView>(R.id.imageView11),
            findViewById<ImageView>(R.id.imageView12),
            findViewById<ImageView>(R.id.imageView13)
        )

        // Recuperar la información de Firestore para los otros ImageViews
        db.collection("eventos")
            .limit(6)
            .get()
            .addOnSuccessListener { documents ->
                documents.forEachIndexed { index, document ->
                    val imageUrl = document.getString("imagenEvento")
                    // Cargar la imagen desde la URL y mostrarla en un ImageView
                    imageUrl?.let {
                        Picasso.get().load(it).into(imageViews[index])
                        // Configurar OnClickListener para cada ImageView
                        imageViews[index].setOnClickListener {
                            val intent = Intent(this, DetalleEventoActivity::class.java).apply {
                                putExtra("eventoId", document.id)
                                putExtra("imagenUrl", imageUrl)
                                putExtra("nombreEvento", document.getString("nombreEvento"))
                                putExtra("fechaEvento", document.getString("fechaEvento"))
                                putExtra("horaEvento", document.getString("horaEvento"))
                                putExtra("ubicacionEvento", document.getString("ubicacionEvento"))
                            }
                            startActivity(intent)
                        }
                    }
                }
            }
    }
}






