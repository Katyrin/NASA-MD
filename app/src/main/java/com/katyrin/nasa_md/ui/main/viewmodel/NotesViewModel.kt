package com.katyrin.nasa_md.ui.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.katyrin.nasa_md.App
import com.katyrin.nasa_md.ui.main.model.data.Note
import com.katyrin.nasa_md.ui.main.model.data.fakeData
import com.katyrin.nasa_md.ui.main.repository.LocalRepository
import com.katyrin.nasa_md.ui.main.repository.LocalRepositoryImpl

class NotesViewModel(
    val liveData: MutableLiveData<AppState> = MutableLiveData(),
    private val noteLocalRepository: LocalRepository =
        LocalRepositoryImpl(App.getNotesDao())
) : ViewModel() {

    fun getFakeNotes(): List<Note> = fakeData

    fun getAllNotes() {
        liveData.value = AppState.Loading
        Thread {
            liveData.postValue(AppState.SuccessLocalQuery(noteLocalRepository.getAllNotes()))
        }.start()
    }

    fun saveNoteToDB(note: Note) {
        Thread {
            noteLocalRepository.saveEntity(note)
        }.start()
    }

    fun deleteNoteToDB(note: Note) {
        Thread {
            noteLocalRepository.deleteEntity(note)
        }.start()
    }

    fun updateNoteToDB(note: Note) {
        Thread {
            noteLocalRepository.updateEntity(note)
        }.start()
    }
}