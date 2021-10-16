package me.igorfedorov.myapp.feature.weather_screen.data.api.model

import me.igorfedorov.myapp.feature.weather_screen.domain.model.*

fun WeatherMainModel.toWeatherMain() = WeatherMain(
    coordinates = coordinatesModel.toCoordinates(),
    weather = weatherModel.map { it.toWeather() },
    base = baseModel,
    main = mainModel.toMain(),
    name = nameModel,
    weatherWind = weatherWindModel.toWind(),
    weatherSystem = weatherSystemModel.toWeatherSystem()
)

fun CoordinatesModel.toCoordinates() = Coordinates(
    lon = lon,
    lat = lat
)

fun MainModel.toMain() = Main(
    temp = temp,
    pressure = pressure,
    humidity = humidity,
    tempMax = tempMax,
    tempMin = tempMin
)

fun WeatherModel.toWeather() = Weather(
    id = id,
    main = main,
    description = description
)

fun WeatherWindModel.toWind() = WeatherWind(
    speed = speed,
    deg = deg
)

fun WeatherSystemModel.toWeatherSystem() = WeatherSystem(
    type = type,
    id = id,
    message = message,
    country = country,
    sunrise = sunrise,
    sunset = sunset
)