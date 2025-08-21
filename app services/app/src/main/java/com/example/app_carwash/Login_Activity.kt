package com.example.app_carwash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

class Login_Activity : AppCompatActivity() {

    lateinit var btnvolver1 : Button
    lateinit var usuario_login : EditText
    lateinit var pass_login : EditText
    lateinit var btnlogin : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnvolver1 = findViewById(R.id.btnvolver1)
        usuario_login = findViewById(R.id.usuario)
        pass_login = findViewById(R.id.pass)
        btnlogin = findViewById(R.id.btnLogin)

        btnlogin.setOnClickListener {
            val user = usuario_login.text.toString()
            val pass = pass_login.text.toString()

            Thread {
                try {
                    val url = URL("http://192.168.1.108:8080/api/login")
                    val conn = url.openConnection() as HttpURLConnection
                    conn.requestMethod = "POST"
                    conn.setRequestProperty("Content-Type", "application/json; utf-8")
                    conn.doOutput = true

                    // Enviar JSON al backend
                    val json = JSONObject()
                    json.put("nombre", user)
                    json.put("clave", pass)

                    val writer = OutputStreamWriter(conn.outputStream)
                    writer.write(json.toString())
                    writer.flush()

                    val responseCode = conn.responseCode
                    val response = BufferedReader(InputStreamReader(conn.inputStream)).readText()

                    runOnUiThread {
                        if (responseCode == 200) {
                            // 👇 Aquí está el cambio importante
                            val jsonResponse = JSONObject(response)
                            if (jsonResponse.getBoolean("success")) {
                                val nombreUsuario = jsonResponse.optString("nombre", "")
                                Toast.makeText(this, "Bienvenido $nombreUsuario", Toast.LENGTH_SHORT).show()
                                startActivity(Intent(this, MainActivity2::class.java))
                                finish()
                            } else {
                                Toast.makeText(this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            Toast.makeText(this, "Error en la conexión", Toast.LENGTH_SHORT).show()
                        }
                    }
                } catch (e: Exception) {
                    runOnUiThread {
                        Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                    }
                }
            }.start()
        }

        btnvolver1.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}