package com.example.app_carwash

import android.app.Activity
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import android.content.Intent


class AgregarProductoActivity : AppCompatActivity() {
    var tbproductos: TableLayout?=null
    lateinit var et1: EditText
    lateinit var et2: EditText
    lateinit var et3: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        et1=findViewById(R.id.et1)
        et2=findViewById(R.id.et2)
        et3=findViewById(R.id.et3)
        val btncerrar=findViewById<Button>(R.id.btnregresar)

        val boton1=findViewById<Button>(R.id.btnagregar)

        tbproductos=findViewById(R.id.tbproductos)
        tbproductos?.removeAllViews()

        boton1.setOnClickListener {

            var codigo=et1.text.toString()
            var descripcion=et2.text.toString()
            var precio=et3.text.toString()

            if(codigo.isEmpty()==false && descripcion.isEmpty()==false && precio.isEmpty()==false){
                llenarTabla()
                et1.setText("")
                et2.setText("")
                et3.setText("")
                Toast.makeText(this, "Se cargaron los datos del artículo", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this,"Los campos deben tener texto", Toast.LENGTH_LONG).show()
            }
        }

        btncerrar.setOnClickListener{
            cerrar()
        }
    }
    fun llenarTabla(){
        //tbproductos?.removeAllViews()

        val registro= LayoutInflater.from(this).inflate(R.layout.listar_layout,null,false)
        val tvCodigo=registro.findViewById<View>(R.id.tvCodigo) as TextView
        val tvDescripcion=registro.findViewById<View>(R.id.tvDescripcion) as TextView
        val tvPrecio=registro.findViewById<View>(R.id.tvPrecio) as TextView
        tvCodigo.setText(et1.text.toString())
        tvDescripcion.setText(et2.text.toString())
        tvPrecio.setText(et3.text.toString())

        tbproductos?.addView(registro)
    }

    fun clickRegistroProducto(view: View){
        resetColorRegistros()
        view.setBackgroundColor(Color.GRAY)
        val registro=view as TableRow
        val controlCodigo=registro.getChildAt(0) as TextView
        val controlnombre=registro.getChildAt(1) as TextView
        val controlprecio=registro.getChildAt(2) as TextView
        val codigo=controlCodigo.text.toString()
        val nombre=controlnombre.text.toString()
        val precio=controlprecio.text.toString()

        if(!codigo.isEmpty()){


            et1?.setText(codigo.toString())
            et2?.setText(nombre.toString())
            et3?.setText(precio.toString())
        }else{
            et1?.setText("")
            et2?.setText("")
            et3?.setText("")
            Toast.makeText(this, "No se ha encontrado ningun registro", Toast.LENGTH_SHORT).show()
        }
    }

    fun resetColorRegistros(){
        for (i in 0 .. tbproductos!!.childCount){
            val registros=tbproductos?.getChildAt(i)
            registros?.setBackgroundColor(Color.WHITE)
        }
    }

    fun cerrar(){
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder
            .setMessage("¿Desea regresar a la página de inicio?")
            .setTitle("Regresar")
            .setPositiveButton(android.R.string.yes) { dialog, which ->
                Toast.makeText(applicationContext, "Regresando a inicio", Toast.LENGTH_SHORT).show()

                // Ir a MA2
                val intent = Intent(this, MainActivity2::class.java)
                startActivity(intent)

                // Cerrar la activity actual para que no quede en el stack
                finish()
            }
            .setNegativeButton(android.R.string.no) { dialog, which ->
                Toast.makeText(applicationContext, "Cancelado", Toast.LENGTH_SHORT).show()
            }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}