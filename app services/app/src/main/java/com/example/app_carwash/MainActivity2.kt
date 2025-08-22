package com.example.app_carwash

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

    val btnServicio = findViewById<Button>(R.id.btnServicio)
    val btnVentas = findViewById<Button>(R.id.btnVentas)
    val btnPersonal = findViewById<Button>(R.id.btnPersonal)
    val btnVehiculo = findViewById<Button>(R.id.btnVehiculo)

        btnServicio.setOnClickListener {
            startActivity(Intent(this, AgregarProductoActivity::class.java))
        }

        btnVentas.setOnClickListener {
            startActivity(Intent(this, VentasActivity::class.java))
        }

        btnPersonal.setOnClickListener {
            startActivity(Intent(this, PersonalActivity::class.java))
        }

        btnVehiculo.setOnClickListener {
            startActivity(Intent(this, VehiculoActivity::class.java))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menuprincipal, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_mapa -> abrirMapa()
            R.id.action_quienes -> startActivity(Intent(this, QuienesSomosActivity::class.java))
            R.id.action_mision -> startActivity(Intent(this, MisionActivity::class.java))
            R.id.action_vision -> startActivity(Intent(this, VisionActivity::class.java))
            R.id.menu_buscar_codigo -> startActivity(Intent(this, BuscarCodigoActivity::class.java))

        }
        return super.onOptionsItemSelected(item)
    }

    private fun abrirMapa() {
        val gmmIntentUri = Uri.parse("geo:0,0?q=-11.9551887,-76.9838733(Transform Wash)")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        startActivity(mapIntent)
    }
}
