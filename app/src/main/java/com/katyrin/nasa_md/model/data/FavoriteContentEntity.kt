package com.katyrin.nasa_md.model.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite")
data class FavoriteContentEntity(
    @PrimaryKey
    val url: String,
    val date: String,
    val description: String
)