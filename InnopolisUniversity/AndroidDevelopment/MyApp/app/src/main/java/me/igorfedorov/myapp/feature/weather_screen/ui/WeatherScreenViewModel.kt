package me.igorfedorov.myapp.feature.weather_screen.ui

import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import android.location.Location
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import me.igorfedorov.myapp.common.Resource
import me.igorfedorov.myapp.feature.weather_screen.domain.model.WeatherMain
import me.igorfedorov.myapp.feature.weather_screen.domain.use_case.get_weather_by_city_use_case.GetWeatherByCityUseCase
import retrofit2.HttpException
import java.io.IOException

class WeatherScreenViewModel(
    private val getWeatherByCityUseCase: GetWeatherByCityUseCase
) : ViewModel() {

    private val _weather = MutableStateFlow<Resource<List<WeatherMain>>>(Resource.Initialized())
    val weather: StateFlow<Resource<List<WeatherMain>>>
        get() = _weather

    /*
    Just a synthetic implementation
    Proper way is to use DataStore for storing cityNames
    Or even DataBase,
    And to request weather on init block for each cityName stored
    Putting them to weatherList, then emitting it to _weather
    As data of Resource.Success()
    * */
    private val weatherList = mutableListOf<WeatherMain>()

    fun requestWeatherByCity(cityName: String) {
        if (cityIsInList(cityName)) return
        viewModelScope.launch {
            try {
                _weather.emit(Resource.Loading())
                val weather = getWeatherByCityUseCase(cityName)
                weatherList.add(weather)
                _weather.emit(Resource.Success(data = weatherList))
            } catch (e: HttpException) {
                _weather.emit(
                    Resource.Error(
                        message = e.localizedMessage ?: "An unexpected error occurred"
                    )
                )
            } catch (e: IOException) {
                _weather.emit(
                    Resource.Error(
                        message = e.localizedMessage
                            ?: "Couldn't reach server. Check your internet connection"
                    )
                )
            }
        }
    }

    private fun cityIsInList(cityName: String): Boolean {
        for (weather in weatherList) {
            if (weather.name == cityName) {
                return true
            }
        }
        return false
    }

    fun deleteFromWeatherList(weatherMain: WeatherMain) {
        viewModelScope.launch {
            weatherList.remove(weatherMain)
            val weatherList = _weather.value.data?.minus(weatherMain) ?: emptyList()
            _weather.emit(Resource.Success(data = weatherList))
        }
    }

    @SuppressLint("MissingPermission")
    fun getCurrentLocation(context: Context, cityName: String?) {
        viewModelScope.launch {
            LocationServices.getFusedLocationProviderClient(context)
                .lastLocation
                .addOnSuccessListener {
                    requestWeatherByCity(
                        cityName
                            ?: getCityNameFromLocation(context, it)
                    )
                }
        }
    }

    private fun getCityNameFromLocation(context: Context, location: Location?): String {
        return location?.let {
            Geocoder(context).getFromLocation(
                it.latitude,
                it.longitude,
                1
            )
                .first()
                .locality
        }
            ?: ""
    }
}

