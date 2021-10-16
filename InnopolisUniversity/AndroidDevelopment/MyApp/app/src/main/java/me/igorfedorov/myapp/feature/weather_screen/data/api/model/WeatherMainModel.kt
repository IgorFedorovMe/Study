package me.igorfedorov.myapp.feature.weather_screen.data.api.model

import com.google.gson.annotations.SerializedName

data class WeatherMainModel(
    @SerializedName("coord")
    val coordinatesModel: CoordinatesModel,
    @SerializedName("weather")
    val weatherModel: List<WeatherModel>,
    @SerializedName("base")
    val baseModel: String,
    @SerializedName("main")
    val mainModel: MainModel,
    @SerializedName("name")
    val nameModel: String,
    @SerializedName("wind")
    val weatherWindModel: WeatherWindModel,
    @SerializedName("sys")
    val weatherSystemModel: WeatherSystemModel
)
