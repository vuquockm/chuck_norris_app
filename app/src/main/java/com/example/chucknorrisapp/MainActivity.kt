package com.example.chucknorrisapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("sorted", JokeList.jokes.toString())
        rvjokes.layoutManager = LinearLayoutManager(this)
        val jokeService= JokeApiServiceFactory.service()
        /*val status=jokeService.giveMeAJoke()*//*.subscribeOn(Schedulers.io())
            .subscribe(
                { result ->  Log.d("onsuccess",result.toString())},
                { error -> Log.e("TAG", "{$error.message}")}
            )*/
        //Log.d("joke",joke.toString())
        val list = JokeList.jokes.toJokes()
        val adapter=JokeAdapter(list)
        rvjokes.adapter=adapter
    }


    //D’abord, comment transformer un String en Joke de test à l’aide d’une extension ?
    fun String.toJoke() = Joke(createdAt = "test", iconUrl = "test",updatedAt = "test",url = "test",id = "test",value=this)

    fun List<String>.toJokes() = map{it.toJoke()}

    //page 33 sur le slide








}
