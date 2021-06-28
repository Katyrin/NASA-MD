package com.katyrin.nasa_md.model.storage

import androidx.room.*
import com.katyrin.nasa_md.model.data.FavoriteContentEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
interface FavoriteContentDao {

    @Query("SELECT * FROM favorite")
    fun getFavorites(): Single<List<FavoriteContentEntity>>

    @Query("SELECT * FROM favorite WHERE url LIKE :url LIMIT 1")
    fun getFavoriteContentByUrl(url: String): Single<FavoriteContentEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun putFavorites(favorites: List<FavoriteContentEntity>): Completable

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun putFavoriteContent(favorite: FavoriteContentEntity): Completable

    @Delete
    fun deleteFavoriteContent(favorite: FavoriteContentEntity): Completable
}