package com.katyrin.nasa_md.presenter

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface LoadingView : MvpView {
    fun showLoadingState()
    fun showNormalState()
}