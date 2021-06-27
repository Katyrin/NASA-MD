package com.katyrin.nasa_md.model.datasorce.appsettings

import com.katyrin.nasa_md.model.data.Theme

interface AppSettingsManager {
    fun saveTheme(theme: Theme)
    fun getTheme(): Int
}