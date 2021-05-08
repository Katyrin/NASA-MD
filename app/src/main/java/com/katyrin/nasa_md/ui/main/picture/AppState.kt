package com.katyrin.nasa_md.ui.main.picture

import com.katyrin.nasa_md.ui.main.model.data.Note

sealed class AppState {
    data class SuccessLocalQuery(val notes: List<Note>): AppState()
    data class Error(val error: Throwable): AppState()
    object Loading: AppState()
}