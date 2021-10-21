package me.igorfedorov.myapp.feature.weather_screen.domain.model

data class Main(
    val temp: Double,
    val pressure: Int,
    val humidity: Int,
    val tempMin: Double,
    val tempMax: Double
) {

    companion object {
        val empty = Main(
            temp = 0.0,
            pressure = 0,
            humidity = 0,
            tempMin = 0.0,
            tempMax = 0.0
        )
    }
}