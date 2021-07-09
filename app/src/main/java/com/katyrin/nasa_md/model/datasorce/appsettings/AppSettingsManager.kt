package com.katyrin.nasa_md.model.datasorce.appsettings

import com.katyrin.nasa_md.model.data.Theme
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable

interface AppSettingsManager {
    fun saveTheme(theme: Theme): Completable
    fun getTheme(): Observable<Int>
}