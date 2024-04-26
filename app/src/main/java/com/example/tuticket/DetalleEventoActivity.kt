package com.example.tuticket

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso

class DetalleEventoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_evento)

        // Obtener los datos pasados desde la actividad principal
        val eventoId = intent.getStringExtra("eventoId")
        val imagenUrl = intent.getStringExtra("imagenUrl")
        val nombreEvento = intent.getStringExtra("nombreEvento")
        val fechaEvento = intent.getStringExtra("fechaEvento")
        val horaEvento = intent.getStringExtra("horaEvento")
        val ubicacionEvento = intent.getStringExtra("ubicacionEvento")

        // Enlazar los TextView con sus IDs
        val textViewNombreEvento = findViewById<TextView>(R.id.textViewNombreEvento)
        val textViewFechaEvento = findViewById<TextView>(R.id.textViewFechaEvento)
        val textViewHoraEvento = findViewById<TextView>(R.id.textViewHoraEvento)
        val textViewUbicacionEvento = findViewById<TextView>(R.id.textViewUbicacionEvento)
        val imageViewDetalle = findViewById<ImageView>(R.id.imageViewDetalle)


        // Mostrar la imagen
        Picasso.get().load(imagenUrl).into(imageViewDetalle)

        // Mostrar los datos del evento
        textViewNombreEvento.text = nombreEvento
        textViewFechaEvento.text = fechaEvento
        textViewHoraEvento.text = horaEvento
        textViewUbicacionEvento.text = ubicacionEvento
    }
}

