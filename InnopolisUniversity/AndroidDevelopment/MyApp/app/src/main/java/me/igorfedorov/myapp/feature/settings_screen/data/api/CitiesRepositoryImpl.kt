package me.igorfedorov.myapp.feature.settings_screen.data.api

import me.igorfedorov.myapp.feature.settings_screen.data.api.model.toCitiesData
import me.igorfedorov.myapp.feature.settings_screen.domain.model.CityData

class CitiesRepositoryImpl(
    private val citiesRemoteSource: CitiesRemoteSource
) : CitiesRepository {
    override suspend fun getCitiesData(cityName: String): List<CityData> {
        return citiesRemoteSource.getCitiesData(cityName).map { it.toCitiesData() }
    }
}