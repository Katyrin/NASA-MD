package com.katyrin.nasa_md.model.datasorce.findsatellitephoto

import com.katyrin.nasa_md.model.api.NasaAPI
import com.katyrin.nasa_md.model.data.SatellitePhotoDTO
import com.katyrin.nasa_md.scheduler.Schedulers
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class FindSatellitePhotoDataSourceImpl @Inject constructor(
    private val nasaAPI: NasaAPI,
    private val schedulers: Schedulers
) : FindSatellitePhotoDataSource {
    override fun requestSatellitePhoto(lat: Float, long: Float): Single<SatellitePhotoDTO> =
        nasaAPI.getSatellitePhotoByLatLong(lat, long)
            .subscribeOn(schedulers.io())
}