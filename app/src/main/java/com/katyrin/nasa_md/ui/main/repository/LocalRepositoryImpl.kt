package com.katyrin.nasa_md.ui.main.repository

import com.katyrin.nasa_md.ui.main.model.data.Note
import com.katyrin.nasa_md.ui.main.model.room.NoteEntity
import com.katyrin.nasa_md.ui.main.model.room.NotesDao

class LocalRepositoryImpl(private val localDataSource: NotesDao): LocalRepository {
    override fun getAllNotes(): List<Note> {
        return convertNoteEntityToNote(localDataSource.all())
    }

    override fun saveEntity(note: Note) {
        localDataSource.insert(convertNoteToEntity(note))
    }

    override fun deleteEntity(note: Note) {
        localDataSource.delete(convertNoteToEntity(note))
    }

    override fun updateEntity(note: Note) {
        localDataSource.update(convertNoteToEntity(note))
    }

    private fun convertNoteEntityToNote(entityList: List<NoteEntity>): List<Note> {
        return entityList.map {
            Note(it.header, it.description, it.isImportant)
        }
    }

    private fun convertNoteToEntity(note: Note): NoteEntity {
        return NoteEntity(note.header, note.description, note.isImportant)
    }
}