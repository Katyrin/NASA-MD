package com.katyrin.nasa_md.presenter.findsatellitephoto

import com.katyrin.nasa_md.model.data.SatellitePhotoDTO
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(OneExecutionStateStrategy::class)
interface FindSatellitePhotoView : MvpView {
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun init()
    fun showErrorLat()
    fun showErrorLong()
    fun enabledButton()
    fun showLoading()
    fun showNormalState()
    fun showRequestError(message: String?)
    fun openNewFragment(satellitePhotoDTO: SatellitePhotoDTO)
}