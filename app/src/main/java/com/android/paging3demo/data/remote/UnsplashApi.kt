package com.android.paging3demo.data.remote

import com.android.paging3demo.BuildConfig
import com.android.paging3demo.model.UnsplashImage
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface UnsplashApi {

    @Headers("Authorization: Client-ID ${BuildConfig.API_KEY}")
    @GET(GET_ALL_IMAGES)
    suspend fun getAllImages(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): List<UnsplashImage>

    @Headers("Authorization: Client-ID ${BuildConfig.API_KEY}")
    @GET(SEARCH_IMAGES)
    suspend fun searchImages(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): List<UnsplashImage>

    companion object{
        const val GET_ALL_IMAGES = "/photos"
        const val SEARCH_IMAGES = "/search/photos"
    }
}