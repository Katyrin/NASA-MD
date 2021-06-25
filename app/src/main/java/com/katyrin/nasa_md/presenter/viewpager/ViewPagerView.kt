package com.katyrin.nasa_md.presenter.viewpager

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface ViewPagerView : MvpView {
    fun init()
}