package com.usiel.tezoexplorer

data class Servicio(
    val nombre: String,
    val descripcion: String,
    val horario: String,
    val telefono: String,
    val latitud: Double,
    val longitud: Double,
    val iconoResId: Int
)
