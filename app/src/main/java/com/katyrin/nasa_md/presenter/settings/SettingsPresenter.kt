package com.katyrin.nasa_md.presenter.settings

import com.katyrin.nasa_md.model.data.Theme
import com.katyrin.nasa_md.model.repository.appsettings.AppSettingsRepository
import com.katyrin.nasa_md.scheduler.Schedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.plusAssign
import moxy.MvpPresenter
import javax.inject.Inject

class SettingsPresenter @Inject constructor(
    private val appSettingsRepository: AppSettingsRepository,
    private val schedulers: Schedulers
) : MvpPresenter<SettingsView>() {

    private var disposable: CompositeDisposable = CompositeDisposable()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.selectChipByTheme(appSettingsRepository.getTheme())
        viewState.setChipsClickListener()
    }

    fun subscribeSetTheme(setTheme: Observable<Theme>) {
        disposable +=
            setTheme
                .distinctUntilChanged()
                .observeOn(schedulers.main())
                .subscribe(::successSetTheme, ::errorSetTheme)
    }

    private fun successSetTheme(theme: Theme) {
        appSettingsRepository.saveTheme(theme)
        viewState.recreateActivity()
    }

    private fun errorSetTheme(throwable: Throwable) {
        viewState.showError(throwable.message)
    }

    override fun onDestroy() {
        disposable.dispose()
        super.onDestroy()
    }
}