package com.katyrin.nasa_md.presenter.favoritecontent

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface FavoriteContentView: MvpView {
    fun init()
    fun successSaveState()
    fun successDeleteState()
    fun showImage(url: String)
    fun showWebView(url: String)
}