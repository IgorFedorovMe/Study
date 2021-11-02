package me.igorfedorov.myapp.feature.weather_screen.domain

import me.igorfedorov.myapp.base.functional.attempt
import me.igorfedorov.myapp.feature.weather_screen.data.api.WeatherRepository

class WeatherInteractor(
    private val repository: WeatherRepository
) {

    suspend fun getWeatherByCity(cityName: String) = attempt {
        repository.getWeatherByCity(cityName)
    }

}