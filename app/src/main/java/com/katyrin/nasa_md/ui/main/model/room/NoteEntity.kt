package com.katyrin.nasa_md.ui.main.model.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NoteEntity(
    @PrimaryKey
    val header: String,
    val description: String,
    val isImportant: Boolean
)
