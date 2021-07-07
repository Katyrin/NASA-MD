package com.katyrin.nasa_md.model.api

import com.katyrin.nasa_md.model.data.DayPictureDTO
import com.katyrin.nasa_md.model.data.SatellitePhotoDTO
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface NasaAPI {

    @GET(APOD)
    fun getPictureOfTheDay(): Single<DayPictureDTO>

    @GET(APOD)
    fun getPictureOfTheDayByDate(
        @Query(DATE) date: String,
        @Query(THUMBS) thumbs: String = TRUE
    ): Single<DayPictureDTO>

    @GET(SATELLITE_PHOTO)
    fun getSatellitePhotoByLatLong(
        @Query(LAT) lat: Float,
        @Query(LON) long: Float,
        @Query(DIM) dim: Float = DIM_VALUE
    ): Single<SatellitePhotoDTO>

    private companion object {
        const val APOD = "planetary/apod"
        const val SATELLITE_PHOTO = "planetary/earth/assets"
        const val DATE = "date"
        const val THUMBS = "thumbs"
        const val TRUE = "true"
        const val LAT = "lat"
        const val LON = "lon"
        const val DIM = "dim"
        const val DIM_VALUE = 0.15f
    }
}
