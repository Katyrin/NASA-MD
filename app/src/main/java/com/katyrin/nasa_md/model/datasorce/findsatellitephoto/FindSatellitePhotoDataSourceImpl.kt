package com.katyrin.nasa_md.model.datasorce.findsatellitephoto

import com.katyrin.nasa_md.model.api.NasaAPI
import com.katyrin.nasa_md.model.data.SatellitePhotoDTO
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class FindSatellitePhotoDataSourceImpl @Inject constructor(
    private val nasaAPI: NasaAPI
) : FindSatellitePhotoDataSource {
    override fun requestSatellitePhoto(lat: Float, long: Float): Single<SatellitePhotoDTO> =
        nasaAPI.getSatellitePhotoByLatLong(lat, long)
            .subscribeOn(Schedulers.io())
}