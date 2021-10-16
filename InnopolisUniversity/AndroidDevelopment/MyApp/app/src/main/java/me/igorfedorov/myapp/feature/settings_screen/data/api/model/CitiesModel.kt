package me.igorfedorov.myapp.feature.settings_screen.data.api.model

import com.google.gson.annotations.SerializedName

data class CitiesModel(
    @SerializedName("data")
    val citiesDataModel: List<CityDataModel>
)