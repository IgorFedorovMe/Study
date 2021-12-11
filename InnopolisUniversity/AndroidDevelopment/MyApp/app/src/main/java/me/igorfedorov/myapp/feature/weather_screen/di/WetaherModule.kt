package me.igorfedorov.myapp.feature.weather_screen.di

import me.igorfedorov.myapp.BuildConfig
import me.igorfedorov.myapp.feature.weather_screen.data.api.WeatherApi
import me.igorfedorov.myapp.feature.weather_screen.data.api.WeatherRemoteSource
import me.igorfedorov.myapp.feature.weather_screen.data.api.WeatherRepository
import me.igorfedorov.myapp.feature.weather_screen.data.api.WeatherRepositoryImpl
import me.igorfedorov.myapp.feature.weather_screen.di.util.ApiKeyInterceptor
import me.igorfedorov.myapp.feature.weather_screen.domain.use_case.get_weather_by_city_use_case.GetWeatherByCityUseCase
import me.igorfedorov.myapp.feature.weather_screen.ui.WeatherScreenViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://api.openweathermap.org/"
private const val OKHTTP_WEATHER = "OKHTTP_WEATHER"
private const val RETROFIT_WEATHER = "RETROFIT_WEATHER"
const val VIEW_MODEL_WEATHER = "VIEW_MODEL_WEATHER"

val weatherModule = module {

    single<OkHttpClient>(named(OKHTTP_WEATHER)) {
        OkHttpClient.Builder()
            .addInterceptor(ApiKeyInterceptor(BuildConfig.WEATHER_API_TOKEN))
            .addNetworkInterceptor(
                HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY)
            )
            .build()
    }

    single<Retrofit>(named(RETROFIT_WEATHER)) {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get<OkHttpClient>(qualifier = named(OKHTTP_WEATHER)))
            .build()
    }

    single<WeatherApi> {
        get<Retrofit>(qualifier = named(RETROFIT_WEATHER)).create(WeatherApi::class.java)
    }

    single<WeatherRemoteSource> {
        WeatherRemoteSource(get<WeatherApi>())
    }

    single<WeatherRepository> {
        WeatherRepositoryImpl(get<WeatherRemoteSource>())
    }

    factory<GetWeatherByCityUseCase> {
        GetWeatherByCityUseCase(get<WeatherRepository>())
    }

    viewModel<WeatherScreenViewModel>(named(VIEW_MODEL_WEATHER)) {
        WeatherScreenViewModel(
            get<GetWeatherByCityUseCase>()
        )
    }
}