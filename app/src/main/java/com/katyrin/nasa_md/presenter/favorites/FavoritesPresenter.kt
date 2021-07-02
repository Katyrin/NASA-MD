package com.katyrin.nasa_md.presenter.favorites

import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.katyrin.nasa_md.model.data.FavoriteContentEntity
import com.katyrin.nasa_md.model.repository.favorites.FavoritesRepository
import com.katyrin.nasa_md.scheduler.Schedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.plusAssign
import moxy.MvpPresenter
import javax.inject.Inject

class FavoritesPresenter @Inject constructor(
    private val favoritesRepository: FavoritesRepository,
    private val router: Router,
    private val schedulers: Schedulers
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
            .observeOn(schedulers.main())
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
            .observeOn(schedulers.main())
            .subscribe(::successUpdateList, ::errorGetFavorites)
    }

    private fun successUpdateList() {

    }

    fun onSaveNewList(newList: List<FavoriteContentEntity>) {
        disposable += favoritesRepository
            .putFavorites(newList)
            .observeOn(schedulers.main())
            .subscribe(::successUpdateList, ::errorGetFavorites)
    }

    fun navigateToScreen(screen: FragmentScreen) {
        router.navigateTo(screen)
    }

    override fun onDestroy() {
        disposable.dispose()
        super.onDestroy()
    }
}