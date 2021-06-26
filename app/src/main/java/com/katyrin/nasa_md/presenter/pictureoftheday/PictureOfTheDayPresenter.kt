package com.katyrin.nasa_md.presenter.pictureoftheday

import com.katyrin.nasa_md.model.data.DayPictureDTO
import com.katyrin.nasa_md.model.repository.home.HomeRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.plusAssign
import moxy.MvpPresenter
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class PictureOfTheDayPresenter @Inject constructor(
    private val homeRepository: HomeRepository
) : MvpPresenter<PictureOfTheDayView>() {

    private var disposable: CompositeDisposable = CompositeDisposable()
    private var isExpanded = false

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
    }

    fun getData(date: String?) {
        viewState.setLoadingState()
        disposable += homeRepository
            .getPictureOfTheDay(date)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(::checkUrl, ::setErrorState)
    }

    private fun checkUrl(dayPictureDTO: DayPictureDTO) {
        viewState.setNormalState()
        if (dayPictureDTO.url.isNullOrEmpty()) viewState.showError(EMPTY_LINK)
        else setSuccessState(dayPictureDTO.url)
    }

    private fun setSuccessState(url: String) {
        val separateUrl = url.split(CHAR_DOT)
        if (separateUrl[1] != YOUTUBE) viewState.showImage(url)
        else viewState.showVideo(url)
    }

    private fun setErrorState(throwable: Throwable) {
        viewState.setNormalState()
        viewState.showError(throwable.message)
    }

    fun subscribeDoubleImageClick(imageClick: Flowable<Boolean>) {
        disposable += imageClick
            .debounce(DEBOUNCE_TIME, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .timeInterval(TimeUnit.MILLISECONDS)
            .skip(SKIP_ONE)
            .filter { interval -> interval.time() < FILTER_TIME }
            .subscribe({ successImageClick() }, ::errorImageClick)
    }

    private fun successImageClick() {
        isExpanded = !isExpanded
        if (isExpanded) viewState.enlargeImage()
        else viewState.reduceImage()
    }

    private fun errorImageClick(throwable: Throwable) {
        viewState.showError(throwable.message)
    }

    override fun onDestroy() {
        disposable.dispose()
        super.onDestroy()
    }

    private companion object {
        const val CHAR_DOT = '.'
        const val YOUTUBE = "youtube"
        const val EMPTY_LINK = "Link is empty"
        const val DEBOUNCE_TIME = 100L
        const val SKIP_ONE = 1L
        const val FILTER_TIME = 500L
    }
}