package com.katyrin.nasa_md.di.modules

import com.katyrin.nasa_md.view.PictureOfTheDayFragment
import com.katyrin.nasa_md.view.viewpager.ViewPagerFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface PicturesModule {

    @ContributesAndroidInjector
    fun viewPagerFragment(): ViewPagerFragment

    @ContributesAndroidInjector
    fun pictureOfTheDayFragment(): PictureOfTheDayFragment
}