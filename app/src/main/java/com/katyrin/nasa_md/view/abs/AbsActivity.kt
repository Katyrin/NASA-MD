package com.katyrin.nasa_md.view.abs

import android.os.Bundle
import androidx.annotation.LayoutRes
import com.katyrin.nasa_md.model.datasorce.appsettings.AppSettingsManager
import com.katyrin.nasa_md.utils.toast
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.plusAssign
import moxy.MvpAppCompatActivity
import javax.inject.Inject

abstract class AbsActivity(@LayoutRes contentLayoutId: Int) : MvpAppCompatActivity(contentLayoutId),
    HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    @Inject
    lateinit var appSettingsManager: AppSettingsManager

    private val disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        subscribeGetTheme()
        super.onCreate(savedInstanceState)
    }

    private fun subscribeGetTheme() {
        disposable += appSettingsManager
            .getTheme()
            .subscribe(::setTheme, ::showError)
    }

    private fun showError(throwable: Throwable) {
        toast(throwable.message)
    }

    override fun androidInjector(): AndroidInjector<Any> = androidInjector
}