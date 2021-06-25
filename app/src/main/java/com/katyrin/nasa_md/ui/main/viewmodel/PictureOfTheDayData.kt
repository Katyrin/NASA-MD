package com.katyrin.nasa_md.ui.main.viewmodel

import com.katyrin.nasa_md.model.data.DayPictureDTO

sealed class PictureOfTheDayData {
    data class Success(val serverResponseData: DayPictureDTO) : PictureOfTheDayData()
    data class Error(val error: Throwable) : PictureOfTheDayData()
    data class Loading(val progress: Int?) : PictureOfTheDayData()
}
