package com.katyrin.nasa_md.model.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import com.katyrin.nasa_md.model.data.FavoriteContentEntity

@Database(entities = [FavoriteContentEntity::class], version = 1, exportSchema = false)
abstract class FavoriteContentDataBase : RoomDatabase() {
    abstract fun favoriteContentDao(): FavoriteContentDao
}