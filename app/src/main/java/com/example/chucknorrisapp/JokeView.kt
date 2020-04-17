package com.example.chucknorrisapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat.startActivity
import kotlinx.android.synthetic.main.joke_layout.view.*
import kotlinx.android.synthetic.main.joke_view_layout.view.*
import kotlinx.serialization.builtins.list
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration


class JokeView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr){
    var btnstate=R.drawable.ic_star_border_black_24dp


    init {
        LayoutInflater.from(context).inflate(R.layout.joke_view_layout,this,true)
    }


    //data class Model(val text:String,val shareIcon:Int,val favoriIcon:Int,val id:String)
    data class Model(val joke: Joke)

    fun setupView(model: Model){
        textview.text=model.joke.value
        btnstate=if(model.joke.saved) R.drawable.ic_star_black_24dp else R.drawable.ic_star_border_black_24dp
        btnfav.setImageResource(btnstate)
        //inflate(context,R.layout.joke_layout,this)
        btnshare.setOnClickListener{
        Log.d("Gbaman", model.joke.id)
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, model.joke.value)
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(context,shareIntent,null)
        }
        btnfav.setOnClickListener{
            Log.d("Kemar",model.joke.id) //log joke id
            btnstate = if(!model.joke.saved){
                saveJoke(model.joke)
                R.drawable.ic_star_black_24dp
            } else{
                unsaveJoke(model.joke)
                R.drawable.ic_star_border_black_24dp
            }
            btnfav.setImageResource(btnstate)


        }


    }

    fun saveJoke(joke: Joke){
        joke.saved=true
        // get shared preferences
        val pref: SharedPreferences = context.getSharedPreferences("jokePref", 0) // 0 - for private mode
        val editor: SharedPreferences.Editor = pref.edit()
        var strJokes=pref.getString("jokes", null)
        //if strJokes is null create jokeList
        var jokes :MutableList<Joke> =  mutableListOf()
        if (strJokes != null){
            jokes = Json(JsonConfiguration.Stable).parse(Joke.serializer().list, strJokes.toString()) as MutableList<Joke>
        }
        jokes.add(joke)
        strJokes = Json(JsonConfiguration.Stable).stringify<MutableList<Joke>>(Joke.serializer().list, jokes)
        editor.putString("jokes",strJokes)
        editor.apply()
    }

    fun unsaveJoke(joke: Joke){
        joke.saved=false
        // get shared preferences
        val pref: SharedPreferences = context.getSharedPreferences("jokePref", 0) // 0 - for private mode
        val editor: SharedPreferences.Editor = pref.edit()
        var strJokes=pref.getString("jokes", null)
        val jokes = Json(JsonConfiguration.Stable).parse(Joke.serializer().list, strJokes.toString()) as MutableList<Joke>
        // get index of joke
        val index = jokes.indexOfFirst{
            it.id == joke.id
        }
        jokes.removeAt(index)
        strJokes = Json(JsonConfiguration.Stable).stringify<MutableList<Joke>>(Joke.serializer().list, jokes)
        editor.putString("jokes",strJokes)
        editor.apply()
    }

}