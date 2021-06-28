package com.katyrin.nasa_md.presenter.findsatellitephoto

import com.katyrin.nasa_md.model.data.Coordinate
import com.katyrin.nasa_md.model.data.SatellitePhotoDTO
import com.katyrin.nasa_md.model.repository.findsatellitephoto.FindSatellitePhotoRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.plusAssign
import io.reactivex.rxjava3.schedulers.Schedulers
import moxy.MvpPresenter
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class FindSatellitePhotoPresenter @Inject constructor(
    private val findSatellitePhotoRepository: FindSatellitePhotoRepository
) : MvpPresenter<FindSatellitePhotoView>() {

    private var disposable: CompositeDisposable = CompositeDisposable()
    private var latIsValid = false
    private var longIsValid = false

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
    }

    fun subscribeLatLongChanged(latLongInput: @NonNull Observable<Pair<Coordinate, String>>) {
        disposable += latLongInput
            .debounce(QUARTER_SECOND, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.computation())
            .distinctUntilChanged()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(::successGetLatLong)
    }

    private fun successGetLatLong(latLongInput: Pair<Coordinate, String>) {
        if (latLongInput.first == Coordinate.LAT)
            latIsValid = isFieldValid(latLongInput.second)
        else
            longIsValid = isFieldValid(latLongInput.second)
        setViewState()
    }

    private fun setViewState() {
        when {
            !latIsValid -> viewState.showErrorLat()
            !longIsValid -> viewState.showErrorLong()
            else -> viewState.enabledButton()
        }
    }

    private fun isFieldValid(field: String): Boolean = field.length > FOUR_CHARS

    fun getSatellitePhoto(lat: Float, long: Float) {
        if (latIsValid && longIsValid) {
            viewState.showLoading()
            requestSatellitePhoto(lat, long)
        }
    }

    private fun requestSatellitePhoto(lat: Float, long: Float) {
        disposable += findSatellitePhotoRepository
            .requestSatellitePhoto(lat, long)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(::setSuccessState, ::setErrorState)
    }

    private fun setSuccessState(satellitePhotoDTO: SatellitePhotoDTO) {
        viewState.showNormalState()
        viewState.openNewFragment(satellitePhotoDTO)
    }

    private fun setErrorState(throwable: Throwable) {
        viewState.showNormalState()
        viewState.showRequestError(throwable.message)
    }

    override fun onDestroy() {
        disposable
        super.onDestroy()
    }

    private companion object {
        const val FOUR_CHARS = 4
        const val QUARTER_SECOND = 250L
    }
}