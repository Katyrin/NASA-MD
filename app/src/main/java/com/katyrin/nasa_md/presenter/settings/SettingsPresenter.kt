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
        getTheme()
        viewState.setChipsClickListener()
    }

    private fun getTheme() {
        disposable += appSettingsRepository
            .getTheme()
            .observeOn(schedulers.main())
            .subscribe(::setTheme, ::error)
    }

    private fun setTheme(themeId: Int) {
        viewState.selectChipByTheme(themeId)
    }

    fun subscribeSetTheme(setTheme: Observable<Theme>) {
        disposable += setTheme
            .distinctUntilChanged()
            .flatMap(::saveTheme)
            .observeOn(schedulers.main())
            .subscribe(
                { successSetTheme() },
                ::error
            )
    }

    private fun saveTheme(theme: Theme): Observable<Unit> =
        appSettingsRepository
            .saveTheme(theme)
            .andThen(Observable.just(Unit))

    private fun successSetTheme() {
        viewState.recreateActivity()
    }

    private fun error(throwable: Throwable) {
        viewState.showError(throwable.message)
    }

    override fun onDestroy() {
        disposable.dispose()
        super.onDestroy()
    }
}