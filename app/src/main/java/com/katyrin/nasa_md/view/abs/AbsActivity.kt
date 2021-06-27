package com.katyrin.nasa_md.view.abs

import android.os.Bundle
import androidx.annotation.LayoutRes
import com.katyrin.nasa_md.model.datasorce.appsettings.AppSettingsManager
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import moxy.MvpAppCompatActivity
import javax.inject.Inject

abstract class AbsActivity(@LayoutRes contentLayoutId: Int) : MvpAppCompatActivity(contentLayoutId),
    HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    @Inject
    lateinit var appSettingsManager: AppSettingsManager

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        val resIdTheme = appSettingsManager.getTheme()
        setTheme(resIdTheme)
        super.onCreate(savedInstanceState)
    }

    override fun androidInjector(): AndroidInjector<Any> = androidInjector

}