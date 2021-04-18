package com.katyrin.nasa_md.ui.main.picture

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PictureOfTheDayAPI {
    @GET("planetary/apod")
    fun getPictureOfTheDay(@Query("api_key") apiKey: String): Call<PODServerResponseData>
}
