package com.katyrin.nasa_md.model.datasorce.storage

import com.katyrin.nasa_md.model.data.DayPictureDTO
import com.katyrin.nasa_md.model.data.FavoriteContentEntity
import com.katyrin.nasa_md.model.data.SatellitePhotoDTO
import com.katyrin.nasa_md.model.datasorce.storage.DataConverter.convertDtoToEntity
import com.katyrin.nasa_md.model.storage.FavoriteContentDataBase
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class MemoryFavoriteDataSourceImpl @Inject constructor(
    private val database: FavoriteContentDataBase
) : MemoryFavoriteDataSource {

    override fun getFavorites(): Single<List<FavoriteContentEntity>> =
        database
            .favoriteContentDao()
            .getFavorites()
            .subscribeOn(Schedulers.io())

    override fun putDayPictureDTO(dayPictureDTO: DayPictureDTO): Completable =
        database
            .favoriteContentDao()
            .putFavoriteContent(convertDtoToEntity(dayPictureDTO))
            .subscribeOn(Schedulers.io())

    override fun putSatellitePhotoDTO(satellitePhotoDTO: SatellitePhotoDTO): Completable =
        database
            .favoriteContentDao()
            .putFavoriteContent(convertDtoToEntity(satellitePhotoDTO))
            .subscribeOn(Schedulers.io())

    override fun putFavoriteContent(favorite: FavoriteContentEntity): Completable =
        database
            .favoriteContentDao()
            .putFavoriteContent(favorite)
            .subscribeOn(Schedulers.io())

    override fun deleteDayPictureDTO(dayPictureDTO: DayPictureDTO): Completable = database
        .favoriteContentDao()
        .deleteFavoriteContent(convertDtoToEntity(dayPictureDTO))
        .subscribeOn(Schedulers.io())

    override fun deleteSatellitePhotoDTO(satellitePhotoDTO: SatellitePhotoDTO): Completable =
        database
            .favoriteContentDao()
            .deleteFavoriteContent(convertDtoToEntity(satellitePhotoDTO))
            .subscribeOn(Schedulers.io())

    override fun deleteFavoriteContent(favorite: FavoriteContentEntity): Completable =
        database
            .favoriteContentDao()
            .deleteFavoriteContent(favorite)
            .subscribeOn(Schedulers.io())
}