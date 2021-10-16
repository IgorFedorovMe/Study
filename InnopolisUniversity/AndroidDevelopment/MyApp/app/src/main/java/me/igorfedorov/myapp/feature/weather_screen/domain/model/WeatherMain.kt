package me.igorfedorov.myapp.feature.weather_screen.domain.model

data class WeatherMain(
    val coordinates: Coordinates,
    val weather: List<Weather>,
    val base: String,
    val main: Main,
    val name: String,
    val weatherWind: WeatherWind,
    val weatherSystem: WeatherSystem
)