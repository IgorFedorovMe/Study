package me.igorfedorov.myapp.feature.settings_screen.data.api.model

import me.igorfedorov.myapp.feature.settings_screen.domain.model.Cities
import me.igorfedorov.myapp.feature.settings_screen.domain.model.CityData


fun CityDataModel.toCitiesData() = CityData(
    city = city,
    country = country,
    name = name
)

fun CitiesModel.toCities() = Cities(
    cityData = citiesDataModel.map { it.toCitiesData() }
)