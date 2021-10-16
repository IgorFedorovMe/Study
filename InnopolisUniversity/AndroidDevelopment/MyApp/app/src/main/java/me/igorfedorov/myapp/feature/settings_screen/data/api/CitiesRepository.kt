package me.igorfedorov.myapp.feature.settings_screen.data.api

import me.igorfedorov.myapp.feature.settings_screen.domain.model.CityData

interface CitiesRepository {

    suspend fun getCitiesData(cityName: String): List<CityData>

}