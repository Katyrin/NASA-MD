package com.katyrin.nasa_md.model.datasorce

import com.katyrin.nasa_md.model.data.DayPictureDTO
import io.reactivex.rxjava3.core.Single

interface HomeDataSource {
    fun getPictureOfTheDay(date: String?): Single<DayPictureDTO>
}