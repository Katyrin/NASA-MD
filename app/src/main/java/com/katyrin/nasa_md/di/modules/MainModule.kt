package com.katyrin.nasa_md.di.modules

import com.katyrin.nasa_md.view.activities.MainActivity
import com.katyrin.nasa_md.view.activities.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface MainModule {

    @ContributesAndroidInjector
    fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector
    fun bindSplashActivity(): SplashActivity
}