package com.katyrin.nasa_md.ui.main.picture

import androidx.lifecycle.ViewModel
import com.katyrin.nasa_md.ui.main.data.ListItem
import com.katyrin.nasa_md.ui.main.data.fakeData

class NotesViewModel : ViewModel() {

    fun getNotes(): List<ListItem> = fakeData
}