package com.katyrin.nasa_md.model.api

import com.katyrin.nasa_md.model.data.DayPictureDTO
import com.katyrin.nasa_md.model.data.SatellitePhotoDTO
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface NasaAPI {

    @GET("planetary/apod")
    fun getPictureOfTheDay(): Single<DayPictureDTO>

    @GET("planetary/apod")
    fun getPictureOfTheDayByDate(
        @Query("date") date: String,
        @Query("thumbs") thumbs: String = TRUE
    ): Single<DayPictureDTO>

    @GET("planetary/earth/assets")
    fun getSatellitePhotoByLatLong(
        @Query("lat") lat: Float,
        @Query("lon") long: Float,
        @Query("dim") dim: Float = DIM_VALUE
    ): Single<SatellitePhotoDTO>

    private companion object {
        const val TRUE = "true"
        const val DIM_VALUE = 0.15f
    }
}
