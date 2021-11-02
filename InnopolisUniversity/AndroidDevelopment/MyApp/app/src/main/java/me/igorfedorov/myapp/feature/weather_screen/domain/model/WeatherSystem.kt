package me.igorfedorov.myapp.feature.weather_screen.domain.model

data class WeatherSystem(
    val type: Int,
    val id: Int,
    val message: Double,
    val country: String,
    val sunrise: Int,
    val sunset: Int
) {

    companion object {
        val empty = WeatherSystem(
            type = 0,
            id = 0,
            message = 0.0,
            country = "",
            sunrise = 0,
            sunset = 0
        )
    }
}
