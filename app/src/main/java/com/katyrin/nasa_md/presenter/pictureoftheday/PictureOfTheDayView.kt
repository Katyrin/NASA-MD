package com.katyrin.nasa_md.presenter.pictureoftheday

import com.katyrin.nasa_md.presenter.ErrorView
import com.katyrin.nasa_md.presenter.LoadingView
import com.katyrin.nasa_md.presenter.main.MainView
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface PictureOfTheDayView : MainView, ErrorView, LoadingView, MvpView {
    fun enlargeImage()
    fun reduceImage()
    fun showImage(url: String)
    fun showVideo(url: String)
    fun successSaveState()
    fun successDeleteState()
}