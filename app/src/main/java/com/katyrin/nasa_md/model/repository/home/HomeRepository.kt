package com.katyrin.nasa_md.model.repository.home

import com.katyrin.nasa_md.model.data.DayPictureDTO
import io.reactivex.rxjava3.core.Single

interface HomeRepository {
    fun getPictureOfTheDay(date: String?): Single<DayPictureDTO>
}