package com.katyrin.nasa_md.view.abs

import moxy.MvpView
import moxy.viewstate.strategy.alias.SingleState

interface LoadingView : MvpView {

    /**
     * Показывает процесс загрузки.
     */
    @SingleState
    fun showLoading()

}