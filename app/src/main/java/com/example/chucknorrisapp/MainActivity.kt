package com.example.chucknorrisapp

import android.app.Activity
import android.content.SharedPreferences
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
import kotlinx.serialization.builtins.list
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration


class MainActivity : Activity() {
    private val disposables = CompositeDisposable()
    private var adapter: JokeAdapter = JokeAdapter{}
    //@SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //get jokes from SharedPref if any
        // get shared preferences
        val pref: SharedPreferences = this.getSharedPreferences("jokePref", 0) // 0 - for private mode
        val editor: SharedPreferences.Editor = pref.edit()
        val strJokes=pref.getString("jokes", null)
        //if strJokes is null create jokeList
        var jokes :MutableList<Joke> =  mutableListOf()
        if (strJokes != null){
            jokes = Json(JsonConfiguration.Stable).parse(Joke.serializer().list, strJokes.toString()) as MutableList<Joke>
        }

        val jokeService= JokeApiServiceFactory.service()
        Log.d("sorted", JokeList.jokes.toString())
        val onBottomReach : (adapter:JokeAdapter)-> Unit= {adapter->
            progressBar.isVisible=true
            //Thread.sleep(5000)
            disposables.add( jokeService.giveMeAJoke().repeat(10).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribeBy(
                //onSuccess = {Log.d("Trump", it.toString())},
                onNext = {
                    adapter.addJoke(it)
                    progressBar.isVisible=false
                },
                onComplete = {Log.e("Warren", "loadboard")}

            ))}
        adapter=JokeAdapter(onBottomReach)

        rvjokes.layoutManager = LinearLayoutManager(this)

        val jokeTouchHelper=JokeTouchHelper(
            onItemMoved = {start:Int, end:Int ->

                    adapter.jokeList.swap(start, end)
                    adapter.notifyItemMoved(start,end)
                    true

            },
            onJokeRemoved = {position: Int ->
                adapter.jokeList.removeAt(position)
                adapter.notifyItemRemoved(position)
            }
        )
        jokeTouchHelper.attachToRecyclerView(rvjokes)

        adapter.jokeList=jokes
        val variable= savedInstanceState?.getString("Serializable")
        if (variable!=null){
            val bundleJokes = Json(JsonConfiguration.Stable).parse(Joke.serializer().list, variable.toString())
            Log.d("troll", bundleJokes.toString())
            adapter.jokeList= bundleJokes as MutableList<Joke>
            //adapter.notifyDataSetChanged()

        }
        else{
            disposables.add( jokeService.giveMeAJoke().repeat(10).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribeBy(
                //onSuccess = {Log.d("Trump", it.toString())},
                onNext = {
                    adapter.addJoke(it)
                },
                onComplete = {Log.e("Warren", "success")}

            ))


        }



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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        val joke = Json(JsonConfiguration.Stable).stringify<MutableList<Joke>>(Joke.serializer().list, adapter.jokeList)
        outState.putString("Serializable",joke)

    }


    //D’abord, comment transformer un String en Joke de test à l’aide d’une extension ?
    fun String.toJoke() = Joke(createdAt = "test", iconUrl = "test",updatedAt = "test",url = "test",id = "test",value=this)

    fun List<String>.toJokes() = map{it.toJoke()}
    //forgot comment

    fun MutableList<Joke>.swap(index1: Int, index2: Int) {
        val tmp = this[index1] // 'this' corresponds to the list
        this[index1] = this[index2]
        this[index2] = tmp
    }

}

