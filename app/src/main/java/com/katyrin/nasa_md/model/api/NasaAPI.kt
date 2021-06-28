package com.katyrin.nasa_md.model.api

import com.katyrin.nasa_md.BuildConfig
import com.katyrin.nasa_md.model.data.DayPictureDTO
import com.katyrin.nasa_md.model.data.SatellitePhotoDTO
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

    @GET("planetary/earth/assets")
    fun getSatellitePhotoByLatLong(
        @Query("lat") lat: Float,
        @Query("lon") long: Float,
        @Query("dim") dim: Float = 0.15f,
        @Query("api_key") apiKey: String = BuildConfig.NASA_API_KEY
    ): Single<SatellitePhotoDTO>
}
