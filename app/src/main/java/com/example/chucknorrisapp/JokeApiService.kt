package com.example.chucknorrisapp

import io.reactivex.Single
import retrofit2.http.GET

interface JokeApiService
{
    @GET("url_path_extension")
    fun giveMeAJoke(): Single<Joke>

}