package me.igorfedorov.myapp.feature.weather_screen.data.api

import me.igorfedorov.myapp.feature.weather_screen.data.api.model.WeatherMainModel

class WeatherRemoteSource(private val api: WeatherApi) {

    suspend fun getWeatherByCity(cityName: String): WeatherMainModel {
        return api.getWeatherByCity(cityName)
    }

}
