package com.katyrin.nasa_md.ui.main.data

sealed class ListItem {

    class NoteItem(
        val header: String,
        val description: String,
        val hasPortfolio: Boolean,
    ) : ListItem()

    class ImportantItem(
        val header: String,
        val description: String,
    ) : ListItem()
}