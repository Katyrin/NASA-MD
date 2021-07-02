package com.katyrin.nasa_md.di.modules

import com.katyrin.nasa_md.model.datasorce.findsatellitephoto.FindSatellitePhotoDataSource
import com.katyrin.nasa_md.model.datasorce.findsatellitephoto.FindSatellitePhotoDataSourceImpl
import com.katyrin.nasa_md.model.repository.findsatellitephoto.FindSatellitePhotoRepository
import com.katyrin.nasa_md.model.repository.findsatellitephoto.FindSatellitePhotoRepositoryImpl
import com.katyrin.nasa_md.model.repository.satellitephoto.SatellitePhotoRepository
import com.katyrin.nasa_md.model.repository.satellitephoto.SatellitePhotoRepositoryImpl
import com.katyrin.nasa_md.view.findsatellitephoto.FindSatellitePhotoFragment
import com.katyrin.nasa_md.view.satellitephoto.SatellitePhotoFragment
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import javax.inject.Singleton

@Module
interface SatellitePhotoModule {

    @Binds
    @Singleton
    fun findSatellitePhotoDataSource(
        findSatellitePhotoDataSourceImpl: FindSatellitePhotoDataSourceImpl
    ): FindSatellitePhotoDataSource

    @Binds
    @Singleton
    fun findSatellitePhotoRepository(
        findSatellitePhotoRepositoryImpl: FindSatellitePhotoRepositoryImpl
    ): FindSatellitePhotoRepository

    @Binds
    @Singleton
    fun satellitePhotoRepository(
        satellitePhotoRepositoryImpl: SatellitePhotoRepositoryImpl
    ): SatellitePhotoRepository

    @ContributesAndroidInjector
    fun findSatellitePhotoFragment(): FindSatellitePhotoFragment

    @ContributesAndroidInjector
    fun satellitePhotoFragment(): SatellitePhotoFragment
}