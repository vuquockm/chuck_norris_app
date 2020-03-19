package com.example.chucknorrisapp

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : Activity() {

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d("sorted", JokeList.jokes.toString())

        rvjokes.layoutManager = LinearLayoutManager(this)
        val jokeService= JokeApiServiceFactory.service()
        jokeService.giveMeAJoke().subscribeOn(Schedulers.io()).subscribeBy(
            onSuccess = {Log.d("Trump", it.toString())},
            onError = {Log.e("Warren", "fail",it)}
        )
        val jokeList = JokeList.jokes.toJokes()
        val adapter=JokeAdapter()
        adapter.jokeList = jokeList
        rvjokes.adapter=adapter
    }

    //D’abord, comment transformer un String en Joke de test à l’aide d’une extension ?
    fun String.toJoke() = Joke(createdAt = "test", iconUrl = "test",updatedAt = "test",url = "test",id = "test",value=this)

    fun List<String>.toJokes() = map{it.toJoke()}
    //forgot comment

}

