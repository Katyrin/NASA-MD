package com.katyrin.nasa_md.model.datasorce.appsettings

import android.content.Context
import android.content.SharedPreferences
import com.katyrin.nasa_md.R
import com.katyrin.nasa_md.model.data.Theme
import javax.inject.Inject

class AppSettingsManagerImpl @Inject constructor(context: Context) : AppSettingsManager {

    private var prefs: SharedPreferences =
        context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

    override fun saveTheme(theme: Theme) {
        val editor = prefs.edit()
        editor.putString(THEME_RES_ID, theme.name)
        editor.apply()
    }

    override fun getTheme(): Int =
        when (prefs.getString(THEME_RES_ID, Theme.SPACE.name)) {
            Theme.MOON.name -> R.style.Theme_NASAMD_Moon
            Theme.MARS.name -> R.style.Theme_NASAMD_Mars
            else -> R.style.Theme_NASAMD
        }

    private companion object {
        const val THEME_RES_ID = "THEME_RES_ID"
    }
}