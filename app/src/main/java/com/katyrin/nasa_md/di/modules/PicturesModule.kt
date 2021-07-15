package com.katyrin.nasa_md.di.modules

import com.katyrin.nasa_md.model.repository.pictureoftheday.PictureOfTheDayRepository
import com.katyrin.nasa_md.model.repository.pictureoftheday.PictureOfTheDayRepositoryImpl
import com.katyrin.nasa_md.view.pictureofthrday.PictureOfTheDayFragment
import com.katyrin.nasa_md.view.viewpager.ViewPagerFragment
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import javax.inject.Singleton

@Module
interface PicturesModule {

    @Binds
    @Singleton
    fun bindPictureOfTheDayRepository(
        pictureOfTheDayRepositoryImpl: PictureOfTheDayRepositoryImpl
    ): PictureOfTheDayRepository

    @ContributesAndroidInjector
    fun bindViewPagerFragment(): ViewPagerFragment

    @ContributesAndroidInjector
    fun bindPictureOfTheDayFragment(): PictureOfTheDayFragment
}