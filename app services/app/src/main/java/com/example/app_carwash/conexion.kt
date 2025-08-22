package com.example.app_carwash

object Conexion {

    private const val BASE_URL = "http://192.168.1.2:8080"

    fun getUrl(endpoint: String): String {
        return "$BASE_URL$endpoint"
    }
}