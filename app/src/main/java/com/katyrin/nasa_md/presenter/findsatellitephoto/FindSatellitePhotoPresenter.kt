package com.katyrin.nasa_md.presenter.findsatellitephoto

import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.katyrin.nasa_md.model.data.Coordinate
import com.katyrin.nasa_md.model.data.SatellitePhotoDTO
import com.katyrin.nasa_md.model.repository.findsatellitephoto.FindSatellitePhotoRepository
import com.katyrin.nasa_md.scheduler.Schedulers
import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.plusAssign
import moxy.MvpPresenter
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class FindSatellitePhotoPresenter @Inject constructor(
    private val findSatellitePhotoRepository: FindSatellitePhotoRepository,
    private val router: Router,
    private val schedulers: Schedulers
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
            .subscribeOn(schedulers.computation())
            .distinctUntilChanged()
            .observeOn(schedulers.main())
            .subscribe(::successGetLatLong, ::setErrorState)
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
            viewState.showLoadingState()
            requestSatellitePhoto(lat, long)
        }
    }

    private fun requestSatellitePhoto(lat: Float, long: Float) {
        disposable += findSatellitePhotoRepository
            .requestSatellitePhoto(lat, long)
            .observeOn(schedulers.main())
            .subscribe(::setSuccessState, ::setErrorState)
    }

    private fun setSuccessState(satellitePhotoDTO: SatellitePhotoDTO) {
        viewState.showNormalState()
        viewState.openNewFragment(satellitePhotoDTO)
    }

    private fun setErrorState(throwable: Throwable) {
        viewState.showNormalState()
        viewState.showError(throwable.message)
    }

    fun navigateToScreen(screen: FragmentScreen) {
        router.navigateTo(screen)
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