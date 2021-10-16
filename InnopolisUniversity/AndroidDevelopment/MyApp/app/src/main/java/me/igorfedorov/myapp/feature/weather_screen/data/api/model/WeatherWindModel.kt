package me.igorfedorov.myapp.feature.weather_screen.data.api.model

import com.google.gson.annotations.SerializedName

data class WeatherWindModel(
    @SerializedName("speed")
    val speed: Double,
    @SerializedName("deg")
    val deg: Int
)
