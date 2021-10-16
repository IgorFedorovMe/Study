package me.igorfedorov.myapp.feature.weather_screen.domain.model

data class WeatherSystem(
    val type: Int,
    val id: Int,
    val message: Double,
    val country: String,
    val sunrise: Int,
    val sunset: Int
)
