package com.katyrin.nasa_md.ui.main.repository

import com.katyrin.nasa_md.ui.main.model.data.Note

interface LocalRepository {
    fun getAllNotes(): List<Note>
    fun saveEntity(note: Note)
    fun deleteEntity(note: Note)
    fun updateEntity(note: Note)
}