package com.example.chucknorrisapp

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.log

class MainActivity : Activity() {
    private val disposables = CompositeDisposable()

    //@SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        Log.d("sorted", JokeList.jokes.toString())
        val adapter=JokeAdapter()

        rvjokes.layoutManager = LinearLayoutManager(this)
        val jokeService= JokeApiServiceFactory.service()
        disposables.add( jokeService.giveMeAJoke().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribeBy(
            //onSuccess = {Log.d("Trump", it.toString())},
            onSuccess = {
                adapter.addJoke(it)
            },
            onError = {Log.e("Warren", "fail",it)}
        ))
        //val jokeList = JokeList.jokes.toJokes()

        //adapter.jokeList = jokeList
        rvjokes.adapter=adapter

        addjokebtn.setOnClickListener {
            progressBar.isVisible=true
            //Thread.sleep(4000)
            disposables.add( jokeService.giveMeAJoke().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribeBy(
                //onSuccess = {Log.d("Trump", it.toString())},
                onSuccess = {
                    adapter.addJoke(it)
                    progressBar.isVisible=false
                },
                onError = {Log.e("Warren", "fail",it)}

            ))

        }



    }

    override fun onStop() {
        disposables.clear()
        super.onStop()

    }


    //D’abord, comment transformer un String en Joke de test à l’aide d’une extension ?
    fun String.toJoke() = Joke(createdAt = "test", iconUrl = "test",updatedAt = "test",url = "test",id = "test",value=this)

    fun List<String>.toJokes() = map{it.toJoke()}
    //forgot comment

}

