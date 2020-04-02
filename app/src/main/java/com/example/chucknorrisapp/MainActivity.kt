package com.example.chucknorrisapp

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

        val jokeService= JokeApiServiceFactory.service()
        Log.d("sorted", JokeList.jokes.toString())
        val onBottomReach : (adapter:JokeAdapter)-> Unit= {adapter->
            progressBar.isVisible=true
            Thread.sleep(5000)
            disposables.add( jokeService.giveMeAJoke().repeat(10).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribeBy(
                //onSuccess = {Log.d("Trump", it.toString())},
                onNext = {
                    adapter.addJoke(it)
                    progressBar.isVisible=false
                },
                onComplete = {Log.e("Warren", "loadboard")}

            ))}
        val adapter=JokeAdapter(onBottomReach)

        rvjokes.layoutManager = LinearLayoutManager(this)



        disposables.add( jokeService.giveMeAJoke().repeat(10).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribeBy(
            //onSuccess = {Log.d("Trump", it.toString())},
            onNext = {
                adapter.addJoke(it)
            },
            onComplete = {Log.e("Warren", "success")}

        ))


        //val jokeList = JokeList.jokes.toJokes()

        //adapter.jokeList = jokeList
        rvjokes.adapter=adapter
        rvjokes.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if(!recyclerView.canScrollVertically(1)){
                    (rvjokes.adapter as JokeAdapter).onBottomReached(rvjokes.adapter as JokeAdapter)
                }
            }

        })

/*
        .setOnClickListener {
            progressBar.isVisible=true
            //Thread.sleep(4000)
            disposables.add( jokeService.giveMeAJoke().repeat(10).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribeBy(
                //onSuccess = {Log.d("Trump", it.toString())},
                onNext = {
                    adapter.addJoke(it)
                    progressBar.isVisible=false
                },
                onComplete = {Log.e("Warren", "loadboard")}

            ))

        }

 */



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

