package com.katyrin.nasa_md.presenter.favorites

import com.katyrin.nasa_md.model.data.FavoriteContentEntity
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface FavoritesView: MvpView {
    fun init()
    fun setLoadingState()
    fun setNormalState()
    fun showEmptyList()
    fun showFavoritesList(listFavorites: List<FavoriteContentEntity>)
    fun showErrorMessage(message: String?)
}