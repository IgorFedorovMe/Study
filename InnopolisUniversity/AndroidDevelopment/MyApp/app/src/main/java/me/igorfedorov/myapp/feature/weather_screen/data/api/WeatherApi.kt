package me.igorfedorov.myapp.feature.weather_screen.data.api

import me.igorfedorov.myapp.feature.weather_screen.data.api.model.WeatherMainModel
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("/data/2.5/weather")
    suspend fun getWeatherByCity(
        @Query("q") cityName: String,
        @Query("units") units: String = "metric",
        @Query("lang") language: String = "ru"
    ): WeatherMainModel


}