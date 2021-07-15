package com.katyrin.nasa_md.model.repository.favorites

import com.katyrin.nasa_md.model.data.FavoriteContentEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface FavoritesRepository {
    fun getFavorites(): Single<List<FavoriteContentEntity>>
    fun deleteFavoriteContent(favorite: FavoriteContentEntity): Completable
    fun deleteAllFavoriteContent(): Completable
    fun putFavorites(favorites: List<FavoriteContentEntity>): Completable
}