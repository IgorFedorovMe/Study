package me.igorfedorov.myapp.feature.weather_screen.di.util

import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor(
    private val apiKey: String
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val modifiedUrl = chain.request().url.newBuilder()
            .addQueryParameter("appid", apiKey)
            .build()

        val modifiedRequest = chain.request().newBuilder()
            .url(modifiedUrl)
            .build()

        return chain.proceed(modifiedRequest)
    }
}