package me.igorfedorov.myapp.feature.weather_screen.data.api

import me.igorfedorov.myapp.feature.weather_screen.domain.model.WeatherMain

interface WeatherRepository {

    suspend fun getWeatherByCity(cityName: String): WeatherMain

}
