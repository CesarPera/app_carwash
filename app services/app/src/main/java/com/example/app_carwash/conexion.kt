package com.example.app_carwash

object Conexion {
    // 👉 Aquí pones la IP o dominio de Railway
    private const val BASE_URL = "http://192.168.3.15:8080"

    fun getUrl(endpoint: String): String {
        return "$BASE_URL$endpoint"
    }
}