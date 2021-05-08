package com.katyrin.nasa_md.ui.main.model.room

import androidx.room.*

@Dao
interface NotesDao {
    @Query("SELECT * FROM NoteEntity")
    fun all(): List<NoteEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: NoteEntity)

    @Update
    fun update(entity: NoteEntity)

    @Delete
    fun delete(entity: NoteEntity)
}
