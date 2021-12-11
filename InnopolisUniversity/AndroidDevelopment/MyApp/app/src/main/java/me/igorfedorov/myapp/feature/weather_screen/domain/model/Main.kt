package me.igorfedorov.myapp.feature.weather_screen.domain.model

data class Main(
    val temp: Double,
    val pressure: Int,
    val humidity: Int,
    val tempMin: Double,
    val tempMax: Double
)