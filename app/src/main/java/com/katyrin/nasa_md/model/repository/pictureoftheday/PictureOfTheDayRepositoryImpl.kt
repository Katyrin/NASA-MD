package com.katyrin.nasa_md.model.repository.pictureoftheday

import com.katyrin.nasa_md.model.data.DayPictureDTO
import com.katyrin.nasa_md.model.data.SatellitePhotoDTO
import com.katyrin.nasa_md.model.datasorce.home.HomeDataSource
import com.katyrin.nasa_md.model.datasorce.storage.MemoryFavoriteDataSource
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class PictureOfTheDayRepositoryImpl @Inject constructor(
    private val homeDataSource: HomeDataSource,
    private val memoryFavoriteDataSource: MemoryFavoriteDataSource
): PictureOfTheDayRepository {

    override fun getPictureOfTheDay(date: String?): Single<DayPictureDTO> =
        homeDataSource.getPictureOfTheDay(date)

    override fun putDayPictureDTO(dayPictureDTO: DayPictureDTO): Completable =
        memoryFavoriteDataSource.putDayPictureDTO(dayPictureDTO)

    override fun deleteDayPictureDTO(dayPictureDTO: DayPictureDTO): Completable =
        memoryFavoriteDataSource.deleteDayPictureDTO(dayPictureDTO)
}