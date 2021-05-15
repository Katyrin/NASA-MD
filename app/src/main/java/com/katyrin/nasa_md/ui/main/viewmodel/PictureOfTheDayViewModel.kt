package com.katyrin.nasa_md.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.katyrin.nasa_md.BuildConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PictureOfTheDayViewModel(
    private val liveDataForViewToObserve: MutableLiveData<PictureOfTheDayData> = MutableLiveData(),
    private val retrofitImpl: PODRetrofitImpl = PODRetrofitImpl()
) : ViewModel() {

    private var isFirstLoad = true

    fun getIsFirstLoad() : Boolean {
        return isFirstLoad
    }

    fun setIsFirstLoad(isFirstLoad: Boolean) {
        this.isFirstLoad = isFirstLoad
    }

    fun getData(date: String?) : LiveData<PictureOfTheDayData> {
        sendServerRequest(date)
        return liveDataForViewToObserve
    }

    private fun sendServerRequest(date: String?) {
        liveDataForViewToObserve.value = PictureOfTheDayData.Loading(null)

        val apiKey: String = BuildConfig.NASA_API_KEY

        if (apiKey.isBlank()) {
            liveDataForViewToObserve.value = PictureOfTheDayData.Error(Throwable("Нужен ключ API"))
        } else {
            if (date.isNullOrEmpty()) {
                retrofitImpl.getRetrofitImpl().getPictureOfTheDay(apiKey)
                    .enqueue(object : Callback<PODServerResponseData> {
                        override fun onResponse(
                            call: Call<PODServerResponseData>,
                            response: Response<PODServerResponseData>
                        ) {
                            if (response.isSuccessful && response.body() != null) {
                                liveDataForViewToObserve.value =
                                    PictureOfTheDayData.Success(response.body()!!)
                            } else {
                                val message = response.message()

                                if (message.isNullOrEmpty()) {
                                    liveDataForViewToObserve.value =
                                        PictureOfTheDayData.Error(Throwable("Неизвестная ошибка"))
                                } else {
                                    liveDataForViewToObserve.value =
                                        PictureOfTheDayData.Error(Throwable(message))
                                }
                            }
                        }

                        override fun onFailure(call: Call<PODServerResponseData>, t: Throwable) {
                            liveDataForViewToObserve.value = PictureOfTheDayData.Error(t)
                        }
                    })
            } else {
                retrofitImpl.getRetrofitImpl().getPictureOfTheDay(date, apiKey)
                    .enqueue(object : Callback<PODServerResponseData> {
                        override fun onResponse(
                            call: Call<PODServerResponseData>,
                            response: Response<PODServerResponseData>
                        ) {
                            if (response.isSuccessful && response.body() != null) {
                                liveDataForViewToObserve.value =
                                    PictureOfTheDayData.Success(response.body()!!)
                            } else {
                                val message = response.message()

                                if (message.isNullOrEmpty()) {
                                    liveDataForViewToObserve.value =
                                        PictureOfTheDayData.Error(Throwable("Неизвестная ошибка"))
                                } else {
                                    liveDataForViewToObserve.value =
                                        PictureOfTheDayData.Error(Throwable(message))
                                }
                            }
                        }

                        override fun onFailure(call: Call<PODServerResponseData>, t: Throwable) {
                            liveDataForViewToObserve.value = PictureOfTheDayData.Error(t)
                        }
                    })
            }

        }
    }
}