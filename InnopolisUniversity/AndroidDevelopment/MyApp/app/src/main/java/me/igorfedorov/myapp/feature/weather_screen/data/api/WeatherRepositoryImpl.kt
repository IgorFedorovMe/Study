package me.igorfedorov.myapp.feature.weather_screen.data.api

import me.igorfedorov.myapp.feature.weather_screen.data.api.model.toWeatherMain
import me.igorfedorov.myapp.feature.weather_screen.domain.model.WeatherMain

class WeatherRepositoryImpl(
    private val weatherRemoteSource: WeatherRemoteSource
) : WeatherRepository {
    override suspend fun getWeatherByCity(cityName: String): WeatherMain {
        return weatherRemoteSource.getWeatherByCity(cityName).toWeatherMain()
    }
}