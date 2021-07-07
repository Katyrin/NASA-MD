package com.katyrin.nasa_md.presenter.favoritecontent

import com.katyrin.nasa_md.presenter.ErrorView
import com.katyrin.nasa_md.presenter.main.MainView
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface FavoriteContentView: MainView, ErrorView, MvpView {
    fun successSaveState()
    fun successDeleteState()
    fun showImage(url: String)
    fun showWebView(url: String)
}