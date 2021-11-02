package me.igorfedorov.myapp.feature.settings_screen.ui

import me.igorfedorov.myapp.base.base_view_model.Event
import me.igorfedorov.myapp.feature.settings_screen.domain.model.CityData

data class SettingsScreenState(
    val cities: List<CityData>,
    val isLoading: Boolean,
    val errorMessage: String
)

sealed class UIEvent() : Event {
    data class GetCities(val cityName: String) : UIEvent()
    data class OnCityItemClicked(val city: CityData) : UIEvent()
}

sealed class DataEvent() : Event {
    object OnLoadData : DataEvent()
    data class SuccessCitiesRequest(val cities: List<CityData>) : DataEvent()
    data class ErrorCitiesRequest(val errorMessage: String) : DataEvent()
}