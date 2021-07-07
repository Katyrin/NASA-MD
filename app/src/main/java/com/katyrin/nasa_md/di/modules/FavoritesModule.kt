package com.katyrin.nasa_md.di.modules

import com.katyrin.nasa_md.model.repository.favoritecontent.FavoriteContentRepository
import com.katyrin.nasa_md.model.repository.favoritecontent.FavoriteContentRepositoryImpl
import com.katyrin.nasa_md.model.repository.favorites.FavoritesRepository
import com.katyrin.nasa_md.model.repository.favorites.FavoritesRepositoryImpl
import com.katyrin.nasa_md.view.favorites.FavoriteContentFragment
import com.katyrin.nasa_md.view.favorites.FavoritesFragment
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import javax.inject.Singleton

@Module
interface FavoritesModule {

    @Binds
    @Singleton
    fun bindFavoritesRepository(favoritesRepositoryImpl: FavoritesRepositoryImpl): FavoritesRepository

    @Binds
    @Singleton
    fun bindFavoriteContentRepository(
        favoriteContentRepositoryImpl: FavoriteContentRepositoryImpl
    ): FavoriteContentRepository

    @ContributesAndroidInjector
    fun bindFavoritesFragment(): FavoritesFragment

    @ContributesAndroidInjector
    fun bindFavoriteContentFragment(): FavoriteContentFragment
}