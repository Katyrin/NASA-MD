package com.katyrin.nasa_md.model.repository.findsatellitephoto

import com.katyrin.nasa_md.model.data.SatellitePhotoDTO
import io.reactivex.rxjava3.core.Single

interface FindSatellitePhotoRepository {
    fun requestSatellitePhoto(lat: Float, long: Float): Single<SatellitePhotoDTO>
}