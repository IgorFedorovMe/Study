package me.igorfedorov.myapp.feature.settings_screen.domain

import me.igorfedorov.myapp.base.functional.attempt
import me.igorfedorov.myapp.feature.settings_screen.data.api.CitiesRepository

class CitiesInteractor(
    private val repository: CitiesRepository
) {

    suspend fun getCitiesData(cityName: String) = attempt {
        repository.getCitiesData(cityName)
    }

}