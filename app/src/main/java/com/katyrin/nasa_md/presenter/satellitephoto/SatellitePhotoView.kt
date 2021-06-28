package com.katyrin.nasa_md.presenter.satellitephoto

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface SatellitePhotoView : MvpView {
    fun init()
    fun successSaveState()
    fun successDeleteState()
}