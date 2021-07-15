package com.katyrin.nasa_md.model.datasorce.storage

import com.katyrin.nasa_md.model.data.DayPictureDTO
import com.katyrin.nasa_md.model.data.FavoriteContentEntity
import com.katyrin.nasa_md.model.data.SatellitePhotoDTO
import com.katyrin.nasa_md.model.datasorce.storage.DataConverter.convertDtoToEntity
import com.katyrin.nasa_md.model.storage.FavoriteContentDataBase
import com.katyrin.nasa_md.scheduler.Schedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class MemoryFavoriteDataSourceImpl @Inject constructor(
    private val database: FavoriteContentDataBase,
    private val schedulers: Schedulers
) : MemoryFavoriteDataSource {

    override fun getFavorites(): Single<List<FavoriteContentEntity>> =
        database
            .favoriteContentDao()
            .getFavorites()
            .subscribeOn(schedulers.io())

    override fun putDayPictureDTO(dayPictureDTO: DayPictureDTO): Completable =
        database
            .favoriteContentDao()
            .putFavoriteContent(convertDtoToEntity(dayPictureDTO))
            .subscribeOn(schedulers.io())

    override fun putSatellitePhotoDTO(satellitePhotoDTO: SatellitePhotoDTO): Completable =
        database
            .favoriteContentDao()
            .putFavoriteContent(convertDtoToEntity(satellitePhotoDTO))
            .subscribeOn(schedulers.io())

    override fun putFavoriteContent(favorite: FavoriteContentEntity): Completable =
        database
            .favoriteContentDao()
            .putFavoriteContent(favorite)
            .subscribeOn(schedulers.io())

    override fun deleteDayPictureDTO(dayPictureDTO: DayPictureDTO): Completable = database
        .favoriteContentDao()
        .deleteFavoriteContent(convertDtoToEntity(dayPictureDTO))
        .subscribeOn(schedulers.io())

    override fun deleteSatellitePhotoDTO(satellitePhotoDTO: SatellitePhotoDTO): Completable =
        database
            .favoriteContentDao()
            .deleteFavoriteContent(convertDtoToEntity(satellitePhotoDTO))
            .subscribeOn(schedulers.io())

    override fun deleteFavoriteContent(favorite: FavoriteContentEntity): Completable =
        database
            .favoriteContentDao()
            .deleteFavoriteContent(favorite)
            .subscribeOn(schedulers.io())

    override fun deleteAllFavoriteContent(): Completable =
        database
            .favoriteContentDao()
            .deleteAllFavoriteContent()
            .subscribeOn(schedulers.io())

    override fun putFavorites(favorites: List<FavoriteContentEntity>): Completable =
        database
            .favoriteContentDao()
            .putFavorites(favorites)
            .subscribeOn(schedulers.io())
}