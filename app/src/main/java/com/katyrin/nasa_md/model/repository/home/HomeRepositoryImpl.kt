package com.katyrin.nasa_md.model.repository.home

import com.katyrin.nasa_md.model.data.DayPictureDTO
import com.katyrin.nasa_md.model.datasorce.home.HomeDataSource
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val homeDataSource: HomeDataSource
) : HomeRepository {
    override fun getPictureOfTheDay(date: String?): Single<DayPictureDTO> =
        homeDataSource.getPictureOfTheDay(date)
}