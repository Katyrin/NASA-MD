package com.katyrin.nasa_md.model.repository.favoritecontent

import com.katyrin.nasa_md.model.data.FavoriteContentEntity
import com.katyrin.nasa_md.model.datasorce.storage.MemoryFavoriteDataSource
import io.reactivex.rxjava3.core.Completable
import javax.inject.Inject

class FavoriteContentRepositoryImpl @Inject constructor(
    private val memoryFavoriteDataSource: MemoryFavoriteDataSource
) : FavoriteContentRepository {
    override fun putFavoriteContent(favorite: FavoriteContentEntity): Completable =
        memoryFavoriteDataSource.putFavoriteContent(favorite)

    override fun deleteFavoriteContent(favorite: FavoriteContentEntity): Completable =
        memoryFavoriteDataSource.deleteFavoriteContent(favorite)
}