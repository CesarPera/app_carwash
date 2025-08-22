package com.example.app_carwash

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONArray
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class VehiculoActivity : AppCompatActivity() {

    private lateinit var edtPlaca: EditText
    private lateinit var edtDescripcion: EditText
    private lateinit var edtFabricante: EditText
    private lateinit var edtModelo: EditText
    private lateinit var edtCliente: EditText
    private lateinit var edtDniCliente: EditText
    private lateinit var edtBrevete: EditText
    private lateinit var edtObservacion: EditText
    private lateinit var tbVehiculos: TableLayout

    // 🔹 Ahora tomamos la URL desde la clase Conexion
    private val urlVehiculos = Conexion.getUrl("/vehiculos")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vehiculo)

        // Inicializar campos
        edtPlaca = EditText(this).apply { hint = "Ingrese placa" }
        (findViewById<androidx.constraintlayout.widget.ConstraintLayout>(R.id.constraintLayout)).addView(edtPlaca)

        edtDescripcion = findViewById(R.id.edtDescripcion)
        edtFabricante = findViewById(R.id.edtFabricante)
        edtModelo = findViewById(R.id.edtModelo)
        edtCliente = findViewById(R.id.edtCliente)
        edtDniCliente = findViewById(R.id.edtDniCliente)
        edtBrevete = findViewById(R.id.edtBrevete)
        edtObservacion = findViewById(R.id.edtObservacion)
        tbVehiculos = findViewById(R.id.tbproductos)

        // Botones
        findViewById<Button>(R.id.btnAgregar).setOnClickListener { crearVehiculo() }
        findViewById<Button>(R.id.btnEditar).setOnClickListener { actualizarVehiculo() }
        findViewById<Button>(R.id.btnEliminar).setOnClickListener { eliminarVehiculo() }
        findViewById<Button>(R.id.btnRegresar).setOnClickListener { finish() }

        listarVehiculos()
    }

    // ================== 🔹 Métodos CRUD ==================
    private fun crearVehiculo() {
        val json = getJsonVehiculo()
        ejecutarPeticion("POST", urlVehiculos, json) {
            runOnUiThread {
                Toast.makeText(this, "Vehículo creado", Toast.LENGTH_LONG).show()
                limpiarCampos()
                listarVehiculos()
            }
        }
    }

    private fun actualizarVehiculo() {
        val json = getJsonVehiculo()
        ejecutarPeticion("PUT", urlVehiculos, json) {
            runOnUiThread {
                Toast.makeText(this, "Vehículo actualizado", Toast.LENGTH_LONG).show()
                limpiarCampos()
                listarVehiculos()
            }
        }
    }

    private fun eliminarVehiculo() {
        val json = JSONObject()
        json.put("placa", edtPlaca.text.toString())
        ejecutarPeticion("DELETE", urlVehiculos, json) {
            runOnUiThread {
                Toast.makeText(this, "Vehículo eliminado", Toast.LENGTH_LONG).show()
                limpiarCampos()
                listarVehiculos()
            }
        }
    }

    private fun listarVehiculos() {
        Thread {
            try {
                val url = URL(urlVehiculos)
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"

                val response = connection.inputStream.bufferedReader().use { it.readText() }
                val array = JSONArray(response)

                runOnUiThread {
                    tbVehiculos.removeAllViews()
                    for (i in 0 until array.length()) {
                        val vehiculo = array.getJSONObject(i)
                        val row = TableRow(this)

                        val placa = TextView(this)
                        placa.text = vehiculo.getString("placa")
                        val modelo = TextView(this)
                        modelo.text = vehiculo.getString("modelo")
                        val cliente = TextView(this)
                        cliente.text = vehiculo.getString("cliente")

                        row.addView(placa)
                        row.addView(modelo)
                        row.addView(cliente)

                        // 🔹 Al hacer click en la fila, llenar los EditText
                        row.setOnClickListener {
                            edtPlaca.setText(vehiculo.getString("placa"))
                            edtDescripcion.setText(vehiculo.getString("descripcion"))
                            edtFabricante.setText(vehiculo.getString("fabricante"))
                            edtModelo.setText(vehiculo.getString("modelo"))
                            edtCliente.setText(vehiculo.getString("cliente"))
                            edtDniCliente.setText(vehiculo.getString("dnicliente"))
                            edtBrevete.setText(vehiculo.getString("brevete"))
                            edtObservacion.setText(vehiculo.getString("observacion"))
                        }

                        tbVehiculos.addView(row)
                    }
                }
                connection.disconnect()
            } catch (e: Exception) {
                e.printStackTrace()
                runOnUiThread {
                    Toast.makeText(this, "Error al listar: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }.start()
    }

    // ================== 🔹 Helpers ==================
    private fun getJsonVehiculo(): JSONObject {
        val json = JSONObject()
        json.put("placa", edtPlaca.text.toString())
        json.put("descripcion", edtDescripcion.text.toString())
        json.put("fabricante", edtFabricante.text.toString())
        json.put("modelo", edtModelo.text.toString())
        json.put("cliente", edtCliente.text.toString())
        json.put("dnicliente", edtDniCliente.text.toString())
        json.put("brevete", edtBrevete.text.toString())
        json.put("observacion", edtObservacion.text.toString())
        return json
    }

    private fun ejecutarPeticion(method: String, urlStr: String, json: JSONObject, callback: (String) -> Unit) {
        Thread {
            try {
                val url = URL(urlStr)
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = method
                connection.setRequestProperty("Content-Type", "application/json; utf-8")
                connection.doOutput = true

                connection.outputStream.use { os ->
                    val input = json.toString().toByteArray(Charsets.UTF_8)
                    os.write(input, 0, input.size)
                }

                val response = connection.inputStream.bufferedReader().use { it.readText() }
                callback(response)
                connection.disconnect()
            } catch (e: Exception) {
                e.printStackTrace()
                runOnUiThread {
                    Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }.start()
    }

    private fun limpiarCampos() {
        edtPlaca.text.clear()
        edtDescripcion.text.clear()
        edtFabricante.text.clear()
        edtModelo.text.clear()
        edtCliente.text.clear()
        edtDniCliente.text.clear()
        edtBrevete.text.clear()
        edtObservacion.text.clear()
    }
}
