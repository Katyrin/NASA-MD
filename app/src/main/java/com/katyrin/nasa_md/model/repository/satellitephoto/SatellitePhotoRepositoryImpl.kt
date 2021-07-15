package com.katyrin.nasa_md.model.repository.satellitephoto

import com.katyrin.nasa_md.model.data.SatellitePhotoDTO
import com.katyrin.nasa_md.model.datasorce.storage.MemoryFavoriteDataSource
import io.reactivex.rxjava3.core.Completable
import javax.inject.Inject

class SatellitePhotoRepositoryImpl @Inject constructor(
    private val memoryFavoriteDataSource: MemoryFavoriteDataSource
) : SatellitePhotoRepository {

    override fun putSatellitePhotoDTO(satellitePhotoDTO: SatellitePhotoDTO): Completable =
        memoryFavoriteDataSource.putSatellitePhotoDTO(satellitePhotoDTO)

    override fun deleteSatellitePhotoDTO(satellitePhotoDTO: SatellitePhotoDTO): Completable =
        memoryFavoriteDataSource.deleteSatellitePhotoDTO(satellitePhotoDTO)
}