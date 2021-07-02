package com.katyrin.nasa_md.presenter.favoritecontent

import com.katyrin.nasa_md.model.data.FavoriteContentEntity
import com.katyrin.nasa_md.model.repository.favoritecontent.FavoriteContentRepository
import com.katyrin.nasa_md.scheduler.Schedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.plusAssign
import moxy.MvpPresenter
import javax.inject.Inject

class FavoriteContentPresenter @Inject constructor(
    private val favoriteContentRepository: FavoriteContentRepository,
    private val schedulers: Schedulers
) : MvpPresenter<FavoriteContentView>() {

    private var disposable: CompositeDisposable = CompositeDisposable()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
    }

    fun checkFavoriteContent(url: String) {
        val separateUrl = url.split(CHAR_DOT)
        if (separateUrl[1] == NASA) viewState.showImage(url)
        else viewState.showWebView(url)
    }

    fun saveFavoriteContent(favoriteContentEntity: FavoriteContentEntity) {
        disposable += favoriteContentRepository
            .putFavoriteContent(favoriteContentEntity)
            .observeOn(schedulers.main())
            .subscribe(::successSave)
    }

    private fun successSave() {
        viewState.successSaveState()
    }

    fun deleteFavoriteContent(favoriteContentEntity: FavoriteContentEntity) {
        disposable += favoriteContentRepository
            .deleteFavoriteContent(favoriteContentEntity)
            .observeOn(schedulers.main())
            .subscribe(::successDelete)
    }

    private fun successDelete() {
        viewState.successDeleteState()
    }

    override fun onDestroy() {
        disposable.dispose()
        super.onDestroy()
    }

    private companion object {
        const val CHAR_DOT = '.'
        const val NASA = "nasa"
    }
}