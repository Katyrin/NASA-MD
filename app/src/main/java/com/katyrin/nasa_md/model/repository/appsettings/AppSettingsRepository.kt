package com.katyrin.nasa_md.model.repository.appsettings

import com.katyrin.nasa_md.model.data.Theme

interface AppSettingsRepository {
    fun saveTheme(theme: Theme)
    fun getTheme(): Int
}