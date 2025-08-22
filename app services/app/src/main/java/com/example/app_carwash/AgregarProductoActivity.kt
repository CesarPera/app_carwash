package com.example.app_carwash

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import android.content.Intent

class AgregarProductoActivity : AppCompatActivity() {
    var tbproductos: TableLayout? = null
    lateinit var et1: EditText
    lateinit var et2: EditText
    lateinit var et3: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_producto)

        et1 = findViewById(R.id.et1)
        et2 = findViewById(R.id.et2)
        et3 = findViewById(R.id.et3)

        val btnCerrar = findViewById<Button>(R.id.btnregresar)
        val btnAgregar = findViewById<Button>(R.id.btnagregar)
        val btnEditar = findViewById<Button>(R.id.btneditar)
        val btnEliminar = findViewById<Button>(R.id.btneliminar)

        tbproductos = findViewById(R.id.tbproductos)
        tbproductos?.removeAllViews()

        // BOTÓN AGREGAR
        btnAgregar.setOnClickListener {
            val codigo = et1.text.toString()
            val descripcion = et2.text.toString()
            val precio = et3.text.toString()

            if (codigo.isNotEmpty() && descripcion.isNotEmpty() && precio.isNotEmpty()) {
                llenarTabla()
                limpiarCampos()
                Toast.makeText(this, "Se cargaron los datos del artículo", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Los campos deben tener texto", Toast.LENGTH_LONG).show()
            }
        }

        // BOTÓN EDITAR
        btnEditar.setOnClickListener {
            editarRegistro()
        }

        // BOTÓN ELIMINAR
        btnEliminar.setOnClickListener {
            eliminarRegistro()
        }

        // BOTÓN REGRESAR
        btnCerrar.setOnClickListener {
            cerrar()
        }
    }

    // 🔹 Agregar registro
    fun llenarTabla() {
        val registro = LayoutInflater.from(this).inflate(R.layout.listar_layout, null, false)
        val tvCodigo = registro.findViewById<View>(R.id.tvCodigo) as TextView
        val tvDescripcion = registro.findViewById<View>(R.id.tvDescripcion) as TextView
        val tvPrecio = registro.findViewById<View>(R.id.tvPrecio) as TextView

        tvCodigo.text = et1.text.toString()
        tvDescripcion.text = et2.text.toString()
        tvPrecio.text = et3.text.toString()

        // Hacemos que cada fila pueda ser seleccionada
        registro.setOnClickListener {
            clickRegistroProducto(registro)
        }

        tbproductos?.addView(registro)
    }

    // 🔹 Seleccionar registro
    fun clickRegistroProducto(view: View) {
        resetColorRegistros()
        view.setBackgroundColor(Color.GRAY)

        val registro = view as TableRow
        val controlCodigo = registro.getChildAt(0) as TextView
        val controlNombre = registro.getChildAt(1) as TextView
        val controlPrecio = registro.getChildAt(2) as TextView

        val codigo = controlCodigo.text.toString()
        val nombre = controlNombre.text.toString()
        val precio = controlPrecio.text.toString()

        if (codigo.isNotEmpty()) {
            et1.setText(codigo)
            et2.setText(nombre)
            et3.setText(precio)
        } else {
            limpiarCampos()
            Toast.makeText(this, "No se ha encontrado ningún registro", Toast.LENGTH_SHORT).show()
        }
    }

    // 🔹 Editar registro seleccionado
    fun editarRegistro() {
        for (i in 0 until tbproductos!!.childCount) {
            val fila = tbproductos?.getChildAt(i) as TableRow
            if ((fila.background as? android.graphics.drawable.ColorDrawable)?.color == Color.GRAY) {
                val tvCodigo = fila.getChildAt(0) as TextView
                val tvDescripcion = fila.getChildAt(1) as TextView
                val tvPrecio = fila.getChildAt(2) as TextView

                tvCodigo.text = et1.text.toString()
                tvDescripcion.text = et2.text.toString()
                tvPrecio.text = et3.text.toString()

                Toast.makeText(this, "Registro editado", Toast.LENGTH_SHORT).show()
                limpiarCampos()
                resetColorRegistros()
                return
            }
        }
        Toast.makeText(this, "Seleccione un registro para editar", Toast.LENGTH_SHORT).show()
    }

    // 🔹 Eliminar registro seleccionado
    fun eliminarRegistro() {
        for (i in 0 until tbproductos!!.childCount) {
            val fila = tbproductos?.getChildAt(i) as TableRow
            if ((fila.background as? android.graphics.drawable.ColorDrawable)?.color == Color.GRAY) {
                tbproductos?.removeView(fila)
                Toast.makeText(this, "Registro eliminado", Toast.LENGTH_SHORT).show()
                limpiarCampos()
                return
            }
        }
        Toast.makeText(this, "Seleccione un registro para eliminar", Toast.LENGTH_SHORT).show()
    }

    // 🔹 Resetear colores de filas
    fun resetColorRegistros() {
        for (i in 0 until tbproductos!!.childCount) {
            val registros = tbproductos?.getChildAt(i)
            registros?.setBackgroundColor(Color.WHITE)
        }
    }

    // 🔹 Limpiar campos
    fun limpiarCampos() {
        et1.setText("")
        et2.setText("")
        et3.setText("")
    }

    // 🔹 Cerrar Activity
    fun cerrar() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder
            .setMessage("¿Desea regresar a la página de inicio?")
            .setTitle("Regresar")
            .setPositiveButton(android.R.string.yes) { _, _ ->
                Toast.makeText(applicationContext, "Regresando a inicio", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, MainActivity2::class.java)
                startActivity(intent)
                finish()
            }
            .setNegativeButton(android.R.string.no) { _, _ ->
                Toast.makeText(applicationContext, "Cancelado", Toast.LENGTH_SHORT).show()
            }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}