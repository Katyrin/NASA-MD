package com.katyrin.nasa_md.presenter.home

import com.katyrin.nasa_md.presenter.ErrorView
import com.katyrin.nasa_md.presenter.LoadingView
import com.katyrin.nasa_md.presenter.main.MainView
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface HomeView : MainView, ErrorView, LoadingView, MvpView {
    fun startAnimation()
    fun showImage(url: String?)
    fun setBottomSheet(title: String, explanation: String)
}