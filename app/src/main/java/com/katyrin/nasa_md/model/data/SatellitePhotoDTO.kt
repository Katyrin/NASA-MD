package com.katyrin.nasa_md.model.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class SatellitePhotoDTO(
    val date: String = "",
    val id: String = "",
    @field:SerializedName("service_version")
    val serviceVersion: String = "",
    val url: String = ""
) : Parcelable
