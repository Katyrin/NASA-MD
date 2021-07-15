package com.katyrin.nasa_md.presenter.favorites

import com.katyrin.nasa_md.model.data.FavoriteContentEntity
import com.katyrin.nasa_md.presenter.ErrorView
import com.katyrin.nasa_md.presenter.LoadingView
import com.katyrin.nasa_md.presenter.main.MainView
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface FavoritesView : MainView, ErrorView, LoadingView, MvpView {
    fun showEmptyList()
    fun showFavoritesList(listFavorites: List<FavoriteContentEntity>)
}