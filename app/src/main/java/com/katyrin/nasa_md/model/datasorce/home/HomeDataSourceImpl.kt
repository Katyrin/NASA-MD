package com.katyrin.nasa_md.model.datasorce.home

import com.katyrin.nasa_md.model.api.NasaAPI
import com.katyrin.nasa_md.model.data.DayPictureDTO
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class HomeDataSourceImpl @Inject constructor(
    private val nasaAPI: NasaAPI
) : HomeDataSource {
    override fun getPictureOfTheDay(date: String?): Single<DayPictureDTO> =
        if (date.isNullOrEmpty()) {
            nasaAPI.getPictureOfTheDay()
                .subscribeOn(Schedulers.io())
        } else {
            nasaAPI.getPictureOfTheDayByDate(date)
                .subscribeOn(Schedulers.io())
        }
}