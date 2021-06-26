package com.katyrin.nasa_md.presenter.pictureoftheday

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface PictureOfTheDayView : MvpView {
    fun init()
    fun showError(message: String?)
    fun enlargeImage()
    fun reduceImage()
    fun setLoadingState()
    fun setNormalState()
    fun showImage(url: String)
    fun showVideo(url: String)
}