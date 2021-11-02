package me.igorfedorov.newsfeedapp.di.util

import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor(private val apiKey: String) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val modifiedRequest = chain.request().newBuilder()
            .addHeader("X-Api-Key", apiKey)
            .build()

        return chain.proceed(modifiedRequest)
    }
}