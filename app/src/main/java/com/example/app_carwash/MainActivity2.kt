package com.example.app_carwash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menuprincipal, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_mapa -> startActivity(Intent(this, MapaActivity::class.java))
            R.id.action_quienes -> startActivity(Intent(this, QuienesSomosActivity::class.java))
            R.id.action_mision -> startActivity(Intent(this, MisionActivity::class.java))
            R.id.action_vision -> startActivity(Intent(this, VisionActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }
}
