package com.katyrin.nasa_md.model.repository.pictureoftheday

import com.katyrin.nasa_md.model.data.DayPictureDTO
import com.katyrin.nasa_md.model.data.SatellitePhotoDTO
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface PictureOfTheDayRepository {
    fun getPictureOfTheDay(date: String?): Single<DayPictureDTO>
    fun putDayPictureDTO(dayPictureDTO: DayPictureDTO): Completable
    fun deleteDayPictureDTO(dayPictureDTO: DayPictureDTO): Completable
}