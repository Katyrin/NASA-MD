package com.katyrin.nasa_md.presenter.home

import com.katyrin.nasa_md.model.data.DayPictureDTO
import com.katyrin.nasa_md.model.repository.home.HomeRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.plusAssign
import moxy.MvpPresenter
import javax.inject.Inject

class HomePresenter @Inject constructor(
    private val homeRepository: HomeRepository
): MvpPresenter<HomeView>() {

    private var disposable: CompositeDisposable = CompositeDisposable()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
        getData(null)
    }

    fun getData(date: String?) {
        viewState.setLoadingState()
        disposable += homeRepository
            .getPictureOfTheDay(date)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(::setSuccessState, ::setErrorState)
    }

    private fun setSuccessState(dayPictureDTO: DayPictureDTO) {
        viewState.startAnimation()
        viewState.setNormalState()
        viewState.showImage(dayPictureDTO.url)
        val title: String = dayPictureDTO.title
        val message: String = dayPictureDTO.explanation
        viewState.setBottomSheet(title, message)
    }

    private fun setErrorState(throwable: Throwable) {
        viewState.setNormalState()
        viewState.showError(throwable)
    }

    override fun onDestroy() {
        disposable.dispose()
        super.onDestroy()
    }
}