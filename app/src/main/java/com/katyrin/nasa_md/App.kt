package com.katyrin.nasa_md

import com.github.terrakok.cicerone.Cicerone
import com.katyrin.nasa_md.di.DaggerAppComponent
import com.katyrin.nasa_md.scheduler.DefaultSchedulers
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class App : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<App> =
        DaggerAppComponent
            .builder()
            .withContext(applicationContext)
            .apply {
                val cicerone = Cicerone.create()
                withRouter(cicerone.router)
                withNavigatorHolder(cicerone.getNavigatorHolder())
            }
            .withSchedulers(DefaultSchedulers)
            .build()

    override fun onCreate() {
        super.onCreate()
        appInstance = this
    }

    companion object {
        private lateinit var appInstance: App
    }
}