package com.katyrin.nasa_md.di

import android.content.Context
import com.katyrin.nasa_md.App
import com.katyrin.nasa_md.di.modules.HomeModule
import com.katyrin.nasa_md.di.modules.NetworkModule
import com.katyrin.nasa_md.di.modules.PicturesModule
import com.katyrin.nasa_md.scheduler.DefaultSchedulers
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidInjectionModule::class, NetworkModule::class, HomeModule::class, PicturesModule::class])
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
        fun withSchedulers(schedulers: DefaultSchedulers): Builder

        fun build(): AppComponent
    }
}