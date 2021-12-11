package me.igorfedorov.myapp.feature.weather_screen.domain.use_case.get_weather_by_city_use_case

import me.igorfedorov.myapp.feature.weather_screen.data.api.WeatherRepository
import me.igorfedorov.myapp.feature.weather_screen.domain.model.WeatherMain

class GetWeatherByCityUseCase(
    private val repository: WeatherRepository
) {

    suspend operator fun invoke(cityName: String): WeatherMain {
        return repository.getWeatherByCity(cityName)
    }

}