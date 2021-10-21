package me.igorfedorov.myapp.feature.weather_screen.ui

import me.igorfedorov.myapp.base.base_view_model.BaseViewModel
import me.igorfedorov.myapp.base.base_view_model.Event
import me.igorfedorov.myapp.feature.weather_screen.domain.WeatherInteractor
import me.igorfedorov.myapp.feature.weather_screen.domain.model.WeatherMain

class WeatherScreenViewModel(
    private val weatherInteractor: WeatherInteractor
) : BaseViewModel<WeatherScreenState>() {

    override fun initialViewState(): WeatherScreenState {
        return WeatherScreenState(
            weatherList = emptyList(),
            weather = WeatherMain.empty,
            isLoading = false,
            errorMessage = null
        )
    }

    override suspend fun reduce(
        event: Event,
        previousState: WeatherScreenState
    ): WeatherScreenState? {
        when (event) {
            is UIEvent.GetWeather -> {
                processDataEvent(DataEvent.OnLoadDataTrue)
                weatherInteractor.getWeatherByCity(event.cityName).fold(
                    onError = {
                        processDataEvent(DataEvent.ErrorWeatherRequest(it.localizedMessage ?: ""))
                    },
                    onSuccess = {
                        processDataEvent(DataEvent.SuccessWeatherRequest(it))
                    }
                )
                processDataEvent(DataEvent.OnLoadDataFalse)
            }
            is UIEvent.OnWeatherItemClick -> {

            }
            is UIEvent.DeleteWeatherFromList -> {
                return previousState.copy(
                    weatherList = previousState.weatherList - event.weather
                )
            }
            is DataEvent.SuccessWeatherRequest -> {
                if (!previousState.weatherList.contains(event.weather))
                    return previousState.copy(
                        weatherList = previousState.weatherList + event.weather
                    )
            }
            is DataEvent.ErrorWeatherRequest -> {
                return previousState.copy(
                    errorMessage = event.errorMessage
                )
            }
            is DataEvent.OnLoadDataTrue -> {
                return previousState.copy(
                    isLoading = true
                )
            }
            is DataEvent.OnLoadDataFalse -> {
                return previousState.copy(
                    isLoading = false
                )
            }
        }
        return null
    }

    fun requestWeatherByCity(cityName: String) {
        processUiEvent(UIEvent.GetWeather(cityName))
    }

    fun deleteFromWeatherList(weatherMain: WeatherMain) {
        processUiEvent(UIEvent.DeleteWeatherFromList(weatherMain))
    }

    fun showMoreWeather(weatherMain: WeatherMain) {

    }
}

