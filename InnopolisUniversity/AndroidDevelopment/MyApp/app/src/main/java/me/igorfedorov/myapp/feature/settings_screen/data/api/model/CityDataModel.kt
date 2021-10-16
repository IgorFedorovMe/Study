package me.igorfedorov.myapp.feature.settings_screen.data.api.model

import com.google.gson.annotations.SerializedName

data class CityDataModel(
    @SerializedName("city")
    val city: String,
    @SerializedName("country")
    val country: String,
    @SerializedName("countryCode")
    val countryCode: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("latitude")
    val latitude: Double,
    @SerializedName("longitude")
    val longitude: Double,
    @SerializedName("name")
    val name: String,
    @SerializedName("population")
    val population: Int,
    @SerializedName("region")
    val region: String,
    @SerializedName("regionCode")
    val regionCode: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("wikiDataId")
    val wikiDataId: String
)