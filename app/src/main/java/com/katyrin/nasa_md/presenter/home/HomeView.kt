package com.katyrin.nasa_md.presenter.home

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface HomeView : MvpView {
    fun startAnimation()
    fun init()
    fun setLoadingState()
    fun showError(throwable: Throwable)
    fun setNormalState()
    fun showImage(url: String?)
    fun setBottomSheet(title: String, explanation: String)
}