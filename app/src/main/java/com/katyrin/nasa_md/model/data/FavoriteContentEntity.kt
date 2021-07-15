package com.katyrin.nasa_md.model.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "favorite")
@Parcelize
data class FavoriteContentEntity(
    @PrimaryKey
    val url: String,
    val date: String,
    val description: String
) : Parcelable