package com.katyrin.nasa_md.model.repository.appsettings

import com.katyrin.nasa_md.model.data.Theme
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable

interface AppSettingsRepository {
    fun saveTheme(theme: Theme): Completable
    fun getTheme(): Observable<Int>
}