package com.katyrin.nasa_md.ui.main.model.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Note(
    val header: String = "",
    val description: String = "",
    val isImportant: Boolean = false
): Parcelable