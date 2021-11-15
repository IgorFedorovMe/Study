package me.igorfedorov.kinonline.feature.movies_screen.data.api

import me.igorfedorov.kinonline.feature.movies_screen.data.api.model.ApiResponseDTO
import retrofit2.http.GET

interface MoviesApi {

    @GET("LukyanovAnatoliy/eca5141dedc79751b3d0b339649e06a3/raw/38f9419762adf27c34a3f052733b296385b6aa8d/")
    suspend fun getAllMovies(): ApiResponseDTO

}