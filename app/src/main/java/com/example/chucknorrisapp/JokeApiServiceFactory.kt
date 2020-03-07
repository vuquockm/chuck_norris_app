package com.example.chucknorrisapp

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

object JokeApiServiceFactory {
    fun service():JokeApiService {
        val builder = Retrofit.Builder()
            .baseUrl("https://api.chucknorris.io/jokes/")
            .addConverterFactory(Json.asConverterFactory(MediaType.get("application/json")))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
        return builder.create(JokeApiService::class.java)

    }
}