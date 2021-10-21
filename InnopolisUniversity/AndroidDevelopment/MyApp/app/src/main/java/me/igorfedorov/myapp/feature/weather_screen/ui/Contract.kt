package me.igorfedorov.myapp.feature.weather_screen.ui

import me.igorfedorov.myapp.base.base_view_model.Event
import me.igorfedorov.myapp.feature.weather_screen.domain.model.WeatherMain

data class WeatherScreenState(
    val weather: WeatherMain,
    val weatherList: List<WeatherMain>,
    val isLoading: Boolean,
    val errorMessage: String?
) {
    val isInErrorState = errorMessage != null
}

sealed class UIEvent() : Event {
    data class GetWeather(val cityName: String) : UIEvent()
    data class OnWeatherItemClick(val weather: WeatherMain) : UIEvent()
    data class DeleteWeatherFromList(val weather: WeatherMain) : UIEvent()
}

sealed class DataEvent() : Event {
    object OnLoadDataTrue : DataEvent()
    object OnLoadDataFalse : DataEvent()
    data class SuccessWeatherRequest(val weather: WeatherMain) : DataEvent()
    data class ErrorWeatherRequest(val errorMessage: String) : DataEvent()
    data class DeleteWeather(val weather: WeatherMain) : DataEvent()
}