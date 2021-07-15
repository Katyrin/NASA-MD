package com.katyrin.nasa_md.model.datasorce.home

import com.katyrin.nasa_md.model.api.NasaAPI
import com.katyrin.nasa_md.model.data.DayPictureDTO
import com.katyrin.nasa_md.scheduler.Schedulers
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class HomeDataSourceImpl @Inject constructor(
    private val nasaAPI: NasaAPI,
    private val schedulers: Schedulers
) : HomeDataSource {
    override fun getPictureOfTheDay(date: String?): Single<DayPictureDTO> =
        Single
            .defer { getCorrectPOTD(date) }
            .subscribeOn(schedulers.io())

    private fun getCorrectPOTD(date: String?): Single<DayPictureDTO> =
        if (date.isNullOrEmpty()) nasaAPI.getPictureOfTheDay()
        else nasaAPI.getPictureOfTheDayByDate(date)
}