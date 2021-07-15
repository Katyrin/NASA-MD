package com.katyrin.nasa_md.presenter.findsatellitephoto

import com.katyrin.nasa_md.model.data.SatellitePhotoDTO
import com.katyrin.nasa_md.presenter.ErrorView
import com.katyrin.nasa_md.presenter.LoadingView
import com.katyrin.nasa_md.presenter.main.MainView
import moxy.MvpView
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(OneExecutionStateStrategy::class)
interface FindSatellitePhotoView : MainView, ErrorView, LoadingView, MvpView {
    fun showErrorLat()
    fun showErrorLong()
    fun enabledButton()
    fun openNewFragment(satellitePhotoDTO: SatellitePhotoDTO)
}