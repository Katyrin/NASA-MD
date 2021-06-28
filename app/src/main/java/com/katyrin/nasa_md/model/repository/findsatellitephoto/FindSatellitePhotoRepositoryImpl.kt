package com.katyrin.nasa_md.model.repository.findsatellitephoto

import com.katyrin.nasa_md.model.data.SatellitePhotoDTO
import com.katyrin.nasa_md.model.datasorce.findsatellitephoto.FindSatellitePhotoDataSource
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class FindSatellitePhotoRepositoryImpl @Inject constructor(
    private val findSatellitePhotoDataSource: FindSatellitePhotoDataSource
) : FindSatellitePhotoRepository {

    override fun requestSatellitePhoto(lat: Float, long: Float): Single<SatellitePhotoDTO> =
        findSatellitePhotoDataSource.requestSatellitePhoto(lat, long)
}