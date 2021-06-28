package com.katyrin.nasa_md.di.modules

import android.content.Context
import androidx.room.Room
import com.katyrin.nasa_md.model.datasorce.storage.MemoryFavoriteDataSource
import com.katyrin.nasa_md.model.datasorce.storage.MemoryFavoriteDataSourceImpl
import com.katyrin.nasa_md.model.storage.FavoriteContentDataBase
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
interface DataBaseModule {

    companion object {
        private const val DB_NAME = "database.db"

        @Singleton
        @Provides
        fun database(context: Context): FavoriteContentDataBase =
            Room.databaseBuilder(context, FavoriteContentDataBase::class.java, DB_NAME).build()
    }

    @Binds
    @Singleton
    fun memoryFavoriteDataSource(
        memoryFavoriteDataSourceImpl: MemoryFavoriteDataSourceImpl
    ): MemoryFavoriteDataSource
}