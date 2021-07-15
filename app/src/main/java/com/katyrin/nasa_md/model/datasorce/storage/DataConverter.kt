package com.katyrin.nasa_md.model.datasorce.storage

import com.katyrin.nasa_md.model.data.DayPictureDTO
import com.katyrin.nasa_md.model.data.FavoriteContentEntity
import com.katyrin.nasa_md.model.data.SatellitePhotoDTO

object DataConverter {

    fun convertDtoToEntity(dayPictureDTO: DayPictureDTO): FavoriteContentEntity =
        FavoriteContentEntity(
            dayPictureDTO.url,
            dayPictureDTO.date,
            dayPictureDTO.title
        )

    fun convertDtoToEntity(satellitePhotoDTO: SatellitePhotoDTO): FavoriteContentEntity =
        FavoriteContentEntity(
            satellitePhotoDTO.url,
            satellitePhotoDTO.date,
            satellitePhotoDTO.id
        )
}