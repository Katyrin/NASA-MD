package com.katyrin.nasa_md.presenter.home

import com.katyrin.nasa_md.model.data.DayPictureDTO
import com.katyrin.nasa_md.model.repository.home.HomeRepository
import com.katyrin.nasa_md.scheduler.Schedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.plusAssign
import moxy.MvpPresenter
import javax.inject.Inject

class HomePresenter @Inject constructor(
    private val homeRepository: HomeRepository,
    private val schedulers: Schedulers
) : MvpPresenter<HomeView>() {

    private var disposable: CompositeDisposable = CompositeDisposable()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
        getData(null)
    }

    fun getData(date: String?) {
        viewState.showLoadingState()
        disposable += homeRepository
            .getPictureOfTheDay(date)
            .observeOn(schedulers.main())
            .subscribe(::setSuccessState, ::setErrorState)
    }

    private fun setSuccessState(dayPictureDTO: DayPictureDTO) {
        viewState.startAnimation()
        viewState.showNormalState()
        viewState.showImage(dayPictureDTO.url)
        val title: String = dayPictureDTO.title
        val message: String = dayPictureDTO.explanation
        viewState.setBottomSheet(title, message)
    }

    private fun setErrorState(throwable: Throwable) {
        viewState.showNormalState()
        viewState.showError(throwable.message)
    }

    override fun onDestroy() {
        disposable.dispose()
        super.onDestroy()
    }
}