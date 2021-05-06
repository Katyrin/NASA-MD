package com.katyrin.nasa_md.ui.main.model.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [NoteEntity::class], version = 1, exportSchema = false)
abstract class NotesDataBase: RoomDatabase() {
    abstract fun notesDao(): NotesDao
}