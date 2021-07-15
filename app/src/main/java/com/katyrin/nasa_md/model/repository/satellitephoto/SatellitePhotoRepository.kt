package com.katyrin.nasa_md.model.repository.satellitephoto

import com.katyrin.nasa_md.model.data.SatellitePhotoDTO
import io.reactivex.rxjava3.core.Completable

interface SatellitePhotoRepository {
    fun putSatellitePhotoDTO(satellitePhotoDTO: SatellitePhotoDTO): Completable
    fun deleteSatellitePhotoDTO(satellitePhotoDTO: SatellitePhotoDTO): Completable
}