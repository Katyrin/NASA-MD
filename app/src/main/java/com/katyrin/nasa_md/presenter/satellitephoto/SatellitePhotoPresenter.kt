package com.katyrin.nasa_md.presenter.satellitephoto

import com.katyrin.nasa_md.model.data.SatellitePhotoDTO
import com.katyrin.nasa_md.model.repository.satellitephoto.SatellitePhotoRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.plusAssign
import moxy.MvpPresenter
import javax.inject.Inject

class SatellitePhotoPresenter @Inject constructor(
    private val satellitePhotoRepository: SatellitePhotoRepository
) : MvpPresenter<SatellitePhotoView>() {

    private var disposable: CompositeDisposable = CompositeDisposable()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
    }

    fun saveSatellitePhoto(satellitePhotoDTO: SatellitePhotoDTO) {
        disposable += satellitePhotoRepository
            .putSatellitePhotoDTO(satellitePhotoDTO)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(::successSave)
    }

    private fun successSave() {
        viewState.successSaveState()
    }

    fun deleteSatellitePhoto(satellitePhotoDTO: SatellitePhotoDTO) {
        disposable += satellitePhotoRepository
            .deleteSatellitePhotoDTO(satellitePhotoDTO)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(::successDelete)
    }

    private fun successDelete() {
        viewState.successDeleteState()
    }

    override fun onDestroy() {
        disposable.dispose()
        super.onDestroy()
    }
}