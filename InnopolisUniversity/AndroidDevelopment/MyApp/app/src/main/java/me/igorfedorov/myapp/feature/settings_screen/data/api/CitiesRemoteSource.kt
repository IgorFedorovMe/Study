package me.igorfedorov.myapp.feature.settings_screen.data.api

import me.igorfedorov.myapp.feature.settings_screen.data.api.model.CityDataModel

class CitiesRemoteSource(private val api: CitiesApi) {

    suspend fun getCitiesData(cityName: String): List<CityDataModel> {
        return api.getCities(cityName).citiesDataModel
    }
}