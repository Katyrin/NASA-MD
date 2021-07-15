package com.katyrin.nasa_md.model.datasorce.findsatellitephoto

import com.katyrin.nasa_md.model.data.SatellitePhotoDTO
import io.reactivex.rxjava3.core.Single

interface FindSatellitePhotoDataSource {
    fun requestSatellitePhoto(lat: Float, long: Float): Single<SatellitePhotoDTO>
}