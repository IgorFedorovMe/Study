package me.igorfedorov.myapp.feature.weather_screen.domain.model

data class WeatherMain(
    val coordinates: Coordinates,
    val weather: List<Weather>,
    val base: String,
    val main: Main,
    val name: String,
    val weatherWind: WeatherWind,
    val weatherSystem: WeatherSystem
) {

    companion object {
        val empty = WeatherMain(
            coordinates = Coordinates.empty,
            weather = emptyList(),
            base = "",
            main = Main.empty,
            name = "",
            weatherWind = WeatherWind.empty,
            weatherSystem = WeatherSystem.empty
        )
    }

}