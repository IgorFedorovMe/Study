package me.igorfedorov.myapp.feature.settings_screen.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import me.igorfedorov.myapp.common.Resource
import me.igorfedorov.myapp.feature.settings_screen.domain.model.CityData
import me.igorfedorov.myapp.feature.settings_screen.domain.use_case.get_cities_data.GetCitiesDataUseCase
import retrofit2.HttpException
import java.io.IOException

class SettingsScreenViewModel(
    private val getCitiesDataUseCase: GetCitiesDataUseCase
) : ViewModel() {

    private val _citiesData = MutableStateFlow<Resource<List<CityData>>>(Resource.Initialized())
    val citiesData: StateFlow<Resource<List<CityData>>>
        get() = _citiesData

    @ExperimentalCoroutinesApi
    @FlowPreview
    fun getCitiesData(cityNameFlow: Flow<String>) {
//        getCitiesDataUseCase(cityName).onEach {
//            _citiesData.value = it
//        }
        cityNameFlow
            .debounce(500)
            .mapLatest { cityName ->
                try {
                    _citiesData.emit(Resource.Loading())
                    val citiesData = getCitiesDataUseCase(cityName)
                    _citiesData.emit(Resource.Success(data = citiesData))
                } catch (e: HttpException) {
                    _citiesData.emit(
                        Resource.Error(
                            message = e.localizedMessage ?: "An unexpected error occurred"
                        )
                    )
                } catch (e: IOException) {
                    _citiesData.emit(
                        Resource.Error(
                            message = e.localizedMessage
                                ?: "Couldn't reach server. Check your internet connection"
                        )
                    )
                }
            }
            .launchIn(viewModelScope)
    }
}