package com.katyrin.nasa_md.di.modules

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.katyrin.nasa_md.BuildConfig
import com.katyrin.nasa_md.model.api.NasaAPI
import dagger.Module
import dagger.Provides
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun apiPost(gson: Gson, client: OkHttpClient): NasaAPI = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(client)
        .build().create(NasaAPI::class.java)

    @Provides
    @Singleton
    fun gson(): Gson = GsonBuilder().setLenient().create()

    @Provides
    @Singleton
    fun clientPost(): OkHttpClient =
        OkHttpClient.Builder().apply {
            addInterceptor(::apiKeyInterceptor)
            addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        }.build()

    private fun apiKeyInterceptor(chain: Interceptor.Chain): Response = chain.run {
        proceed(
            request()
                .newBuilder()
                .url(
                    request().url
                        .newBuilder()
                        .addQueryParameter(API_KEY, BuildConfig.NASA_API_KEY)
                        .build()
                )
                .build()
        )
    }

    private companion object {
        const val BASE_URL = "https://api.nasa.gov/"
        const val API_KEY = "api_key"
    }
}