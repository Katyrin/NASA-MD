package com.katyrin.nasa_md.model.api

import com.katyrin.nasa_md.BuildConfig
import com.katyrin.nasa_md.model.data.DayPictureDTO
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface NasaAPI {

    @GET("planetary/apod")
    fun getPictureOfTheDay(
        @Query("api_key") apiKey: String = BuildConfig.NASA_API_KEY
    ): Single<DayPictureDTO>

    @GET("planetary/apod")
    fun getPictureOfTheDayByDate(
        @Query("date") date: String,
        @Query("api_key") apiKey: String = BuildConfig.NASA_API_KEY,
        @Query("thumbs") thumbs: String = "true"
    ): Single<DayPictureDTO>
}
