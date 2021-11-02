package me.igorfedorov.myapp.feature.weather_screen.domain.model

data class Coordinates(
    val lon: Double,
    val lat: Double
) {

    companion object {
        val empty = Coordinates(
            lon = 0.0,
            lat = 0.0
        )
    }
}