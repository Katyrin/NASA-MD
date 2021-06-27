package com.katyrin.nasa_md.di.modules

import com.katyrin.nasa_md.model.datasorce.home.HomeDataSource
import com.katyrin.nasa_md.model.datasorce.home.HomeDataSourceImpl
import com.katyrin.nasa_md.model.repository.home.HomeRepository
import com.katyrin.nasa_md.model.repository.home.HomeRepositoryImpl
import com.katyrin.nasa_md.presenter.home.HomePresenter
import com.katyrin.nasa_md.view.HomeFragment
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import javax.inject.Singleton

@Module
interface HomeModule {

    @Singleton
    @Binds
    fun homeDataSource(homeDataSourceImpl: HomeDataSourceImpl): HomeDataSource

    @Singleton
    @Binds
    fun homeRepository(homeRepositoryImpl: HomeRepositoryImpl): HomeRepository

    @ContributesAndroidInjector
    fun bindHomePresenter(): HomePresenter

    @ContributesAndroidInjector
    fun bindHomeFragment(): HomeFragment
}