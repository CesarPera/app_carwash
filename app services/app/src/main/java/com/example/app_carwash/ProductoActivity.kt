package com.example.app_carwash

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.*



class ProductoActivity : AppCompatActivity() {
    private lateinit var spinnerCategorias: Spinner
    private lateinit var spinnerProductos: Spinner
    private lateinit var btnAgregar: Button

    private val categorias = listOf("Lavado", "Pulido", "Aspirado")

    // Mapa de categorías y productos
    private val productosMap = mutableMapOf(
        "Lavado" to mutableListOf("L001 - Lavado básico", "L002 - Lavado premium"),
        "Pulido" to mutableListOf("P001 - Pulido simple", "P002 - Pulido completo"),
        "Aspirado" to mutableListOf("A001 - Aspirado interior", "A002 - Aspirado profundo")
    )

    private var categoriaSeleccionada: String = categorias[0]

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        spinnerCategorias = findViewById(R.id.spinnerCategorias)
        spinnerProductos = findViewById(R.id.spinnerProductos)
        btnAgregar = findViewById(R.id.btnAgregar)

        // Adaptador de categorías
        val categoriasAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categorias)
        categoriasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCategorias.adapter = categoriasAdapter

        // Mostrar productos de la primera categoría por defecto
        actualizarProductos(categorias[0])

        // Cambio de categoría
        spinnerCategorias.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: android.view.View?, position: Int, id: Long) {
                categoriaSeleccionada = categorias[position]
                actualizarProductos(categoriaSeleccionada)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        // Botón para ir a agregar producto
        btnAgregar.setOnClickListener {
            val intent = Intent(this, AgregarProductoActivity::class.java)
            startActivityForResult(intent, 1)
        }
    }

    // Función para actualizar productos según la categoría
    private fun actualizarProductos(categoria: String) {
        val productos = productosMap[categoria] ?: mutableListOf()
        val productosAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, productos)
        productosAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerProductos.adapter = productosAdapter
    }

    // Recibir datos desde la segunda pantalla
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            val codigo = data?.getStringExtra("codigo")
            val nombre = data?.getStringExtra("nombre")

            if (!codigo.isNullOrEmpty() && !nombre.isNullOrEmpty()) {
                productosMap[categoriaSeleccionada]?.add("$codigo - $nombre")
                actualizarProductos(categoriaSeleccionada)
            }
        }
    }
}