package com.katyrin.nasa_md.presenter.favorites

import com.katyrin.nasa_md.model.data.FavoriteContentEntity
import com.katyrin.nasa_md.model.repository.favorites.FavoritesRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.plusAssign
import moxy.MvpPresenter
import javax.inject.Inject

class FavoritesPresenter @Inject constructor(
    private val favoritesRepository: FavoritesRepository
) : MvpPresenter<FavoritesView>() {

    private var disposable: CompositeDisposable = CompositeDisposable()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
        getFavorites()
    }

    private fun getFavorites() {
        viewState.setLoadingState()
        disposable += favoritesRepository
            .getFavorites()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(::successGetFavorites, ::errorGetFavorites)
    }

    private fun successGetFavorites(listFavorites: List<FavoriteContentEntity>) {
        viewState.setNormalState()
        if (listFavorites.isEmpty())
            viewState.showEmptyList()
        else
            viewState.showFavoritesList(listFavorites)
    }

    private fun errorGetFavorites(throwable: Throwable) {
        viewState.setNormalState()
        viewState.showErrorMessage(throwable.message)
    }

    fun deleteFavorite(favoriteContentEntity: FavoriteContentEntity) {
        disposable += favoritesRepository
            .deleteFavoriteContent(favoriteContentEntity)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(::successUpdateList, ::errorGetFavorites)
    }

    private fun successUpdateList() {

    }

    fun onSaveNewList(newList: List<FavoriteContentEntity>) {
        disposable += favoritesRepository
            .deleteAllFavoriteContent()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ updateList(newList) }, ::errorGetFavorites)
    }

    private fun updateList(newList: List<FavoriteContentEntity>) {
        disposable += favoritesRepository
            .putFavorites(newList)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(::successUpdateList, ::errorGetFavorites)
    }

    override fun onDestroy() {
        disposable.dispose()
        super.onDestroy()
    }
}