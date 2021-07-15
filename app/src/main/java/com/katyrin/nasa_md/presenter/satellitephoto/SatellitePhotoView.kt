package com.katyrin.nasa_md.presenter.satellitephoto

import com.katyrin.nasa_md.presenter.ErrorView
import com.katyrin.nasa_md.presenter.main.MainView
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface SatellitePhotoView : MainView, ErrorView, MvpView {
    fun successSaveState()
    fun successDeleteState()
}