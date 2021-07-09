package com.katyrin.nasa_md.model.datasorce.appsettings

import android.content.Context
import android.content.SharedPreferences
import com.katyrin.nasa_md.R
import com.katyrin.nasa_md.model.data.Theme
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class AppSettingsManagerImpl @Inject constructor(context: Context) : AppSettingsManager {

    private var prefs: SharedPreferences =
        context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

    override fun saveTheme(theme: Theme): Completable =
        Completable.fromCallable {
            val editor = prefs.edit()
            editor.putString(THEME_RES_ID, theme.name)
            editor.apply()
        }

    override fun getTheme(): Observable<Int> =
        when (prefs.getString(THEME_RES_ID, Theme.SPACE.name)) {
            Theme.MOON.name -> Observable.fromCallable { R.style.Theme_NASAMD_Moon }
            Theme.MARS.name -> Observable.fromCallable { R.style.Theme_NASAMD_Mars }
            else -> Observable.fromCallable { R.style.Theme_NASAMD }
        }

    private companion object {
        const val THEME_RES_ID = "THEME_RES_ID"
    }
}