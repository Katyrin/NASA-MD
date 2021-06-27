package com.katyrin.nasa_md.model.repository.appsettings

import com.katyrin.nasa_md.model.data.Theme
import com.katyrin.nasa_md.model.datasorce.appsettings.AppSettingsManager
import javax.inject.Inject

class AppSettingsRepositoryImpl @Inject constructor(
    private val appSettingsManager: AppSettingsManager
) : AppSettingsRepository {
    override fun saveTheme(theme: Theme) {
        appSettingsManager.saveTheme(theme)
    }

    override fun getTheme(): Int = appSettingsManager.getTheme()
}