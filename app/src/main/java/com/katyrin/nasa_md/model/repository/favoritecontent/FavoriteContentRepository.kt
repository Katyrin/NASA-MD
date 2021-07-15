package com.katyrin.nasa_md.model.repository.favoritecontent

import com.katyrin.nasa_md.model.data.FavoriteContentEntity
import io.reactivex.rxjava3.core.Completable

interface FavoriteContentRepository {
    fun putFavoriteContent(favorite: FavoriteContentEntity): Completable
    fun deleteFavoriteContent(favorite: FavoriteContentEntity): Completable
}