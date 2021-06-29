package com.katyrin.nasa_md.model.repository.favorites

import com.katyrin.nasa_md.model.data.FavoriteContentEntity
import com.katyrin.nasa_md.model.datasorce.storage.MemoryFavoriteDataSource
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class FavoritesRepositoryImpl @Inject constructor(
    private val memoryFavoriteDataSource: MemoryFavoriteDataSource
) : FavoritesRepository {

    override fun getFavorites(): Single<List<FavoriteContentEntity>> =
        memoryFavoriteDataSource.getFavorites()

    override fun deleteFavoriteContent(favorite: FavoriteContentEntity): Completable =
        memoryFavoriteDataSource.deleteFavoriteContent(favorite)

    override fun deleteAllFavoriteContent(): Completable =
        memoryFavoriteDataSource.deleteAllFavoriteContent()

    override fun putFavorites(favorites: List<FavoriteContentEntity>): Completable =
        memoryFavoriteDataSource.putFavorites(favorites)
}