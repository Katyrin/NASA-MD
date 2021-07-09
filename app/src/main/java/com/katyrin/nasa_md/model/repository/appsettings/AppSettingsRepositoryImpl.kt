package com.katyrin.nasa_md.model.repository.appsettings

import com.katyrin.nasa_md.model.data.Theme
import com.katyrin.nasa_md.model.datasorce.appsettings.AppSettingsManager
import com.katyrin.nasa_md.scheduler.Schedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class AppSettingsRepositoryImpl @Inject constructor(
    private val appSettingsManager: AppSettingsManager,
    private val schedulers: Schedulers
) : AppSettingsRepository {
    override fun saveTheme(theme: Theme): Completable =
        appSettingsManager
            .saveTheme(theme)
            .subscribeOn(schedulers.io())

    override fun getTheme(): Observable<Int> =
        appSettingsManager
            .getTheme()
            .subscribeOn(schedulers.io())
}