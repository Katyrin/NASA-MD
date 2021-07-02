package com.katyrin.nasa_md.di

import android.content.Context
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.katyrin.nasa_md.App
import com.katyrin.nasa_md.di.modules.*
import com.katyrin.nasa_md.scheduler.DefaultSchedulers
import com.katyrin.nasa_md.scheduler.Schedulers
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        NetworkModule::class,
        HomeModule::class,
        PicturesModule::class,
        SettingsModule::class,
        MainModule::class,
        SatellitePhotoModule::class,
        DataBaseModule::class,
        FavoritesModule::class
    ]
)
interface AppComponent : AndroidInjector<App> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun withContext(context: Context): Builder

        @BindsInstance
        fun withRouter(router: Router): Builder

        @BindsInstance
        fun withNavigatorHolder(navigatorHolder: NavigatorHolder): Builder

        @BindsInstance
        fun withSchedulers(schedulers: Schedulers): Builder

        fun build(): AppComponent
    }
}