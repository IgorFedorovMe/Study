package me.igorfedorov.myapp.feature.weather_screen.domain.model

data class WeatherWind(
    val speed: Double,
    val deg: Int
) {

    companion object {
        val empty = WeatherWind(
            speed = 0.0,
            deg = 0
        )
    }
}
