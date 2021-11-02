package me.igorfedorov.myapp.feature.settings_screen.ui

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.mapLatest
import me.igorfedorov.myapp.base.base_view_model.BaseViewModel
import me.igorfedorov.myapp.base.base_view_model.Event
import me.igorfedorov.myapp.feature.settings_screen.domain.CitiesInteractor

class SettingsScreenViewModel(
    private val citiesInteractor: CitiesInteractor
) : BaseViewModel<SettingsScreenState>() {

    override fun initialViewState(): SettingsScreenState {
        return SettingsScreenState(
            cities = emptyList(),
            isLoading = false,
            errorMessage = ""
        )
    }

    override suspend fun reduce(
        event: Event,
        previousState: SettingsScreenState
    ): SettingsScreenState? {
        when (event) {
            is UIEvent.GetCities -> {
                processDataEvent(DataEvent.OnLoadData)
                citiesInteractor.getCitiesData(event.cityName).fold(
                    onError = {
                        processDataEvent(DataEvent.ErrorCitiesRequest(it.localizedMessage ?: ""))
                    },
                    onSuccess = {
                        processDataEvent(DataEvent.SuccessCitiesRequest(it))
                    }
                )
            }
            is DataEvent.OnLoadData -> {
                return previousState.copy(isLoading = true)
            }
            is DataEvent.SuccessCitiesRequest -> {
                return previousState.copy(
                    cities = event.cities,
                    isLoading = false
                )
            }
            is DataEvent.ErrorCitiesRequest -> {
                return previousState.copy(
                    errorMessage = event.errorMessage,
                    isLoading = false
                )
            }
        }
        return null
    }

    fun getCitiesData(cityNameFlow: Flow<String>) {
        cityNameFlow
            .debounce(500)
            .mapLatest { cityName ->
                processUiEvent(UIEvent.GetCities(cityName))
            }
            .launchIn(viewModelScope)
    }
}