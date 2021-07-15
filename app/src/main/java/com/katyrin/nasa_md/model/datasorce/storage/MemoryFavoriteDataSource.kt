package com.katyrin.nasa_md.model.datasorce.storage

import com.katyrin.nasa_md.model.data.DayPictureDTO
import com.katyrin.nasa_md.model.data.FavoriteContentEntity
import com.katyrin.nasa_md.model.data.SatellitePhotoDTO
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface MemoryFavoriteDataSource {
    fun getFavorites(): Single<List<FavoriteContentEntity>>
    fun putDayPictureDTO(dayPictureDTO: DayPictureDTO): Completable
    fun deleteDayPictureDTO(dayPictureDTO: DayPictureDTO): Completable
    fun putSatellitePhotoDTO(satellitePhotoDTO: SatellitePhotoDTO): Completable
    fun deleteSatellitePhotoDTO(satellitePhotoDTO: SatellitePhotoDTO): Completable
    fun putFavoriteContent(favorite: FavoriteContentEntity): Completable
    fun deleteFavoriteContent(favorite: FavoriteContentEntity): Completable
    fun deleteAllFavoriteContent(): Completable
    fun putFavorites(favorites: List<FavoriteContentEntity>): Completable
}