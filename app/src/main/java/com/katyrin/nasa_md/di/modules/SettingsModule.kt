package com.katyrin.nasa_md.di.modules

import com.katyrin.nasa_md.model.datasorce.appsettings.AppSettingsManager
import com.katyrin.nasa_md.model.datasorce.appsettings.AppSettingsManagerImpl
import com.katyrin.nasa_md.model.repository.appsettings.AppSettingsRepository
import com.katyrin.nasa_md.model.repository.appsettings.AppSettingsRepositoryImpl
import com.katyrin.nasa_md.view.settings.SettingsFragment
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import javax.inject.Singleton

@Module
interface SettingsModule {

    @Binds
    @Singleton
    fun bindAppSettingsManager(appSettingsManagerImpl: AppSettingsManagerImpl): AppSettingsManager

    @Binds
    @Singleton
    fun bindAppSettingsRepository(
        appSettingsRepositoryImpl: AppSettingsRepositoryImpl
    ): AppSettingsRepository

    @ContributesAndroidInjector
    fun bindSettingsFragment(): SettingsFragment
}