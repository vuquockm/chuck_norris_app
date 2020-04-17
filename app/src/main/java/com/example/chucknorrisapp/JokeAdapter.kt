package com.example.chucknorrisapp

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class JokeAdapter(var onBottomReached:()->Unit) : RecyclerView.Adapter<JokeAdapter.JokeViewHolder>() {

    var jokeList: MutableList<Joke> = mutableListOf()
        set(jokes) {
            field = jokes
            notifyDataSetChanged()
        }



    class JokeViewHolder(val jokeview: JokeView) : RecyclerView.ViewHolder(jokeview)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JokeViewHolder {
        // create a new view
        val jokeView = LayoutInflater.from(parent.context)
           .inflate(R.layout.joke_layout, parent, false) as JokeView
        // set the view's size, margins, paddings and layout parameters
        //val jokeView=JokeView(parent.context)

        return JokeViewHolder(jokeView)
    }

    override fun getItemCount(): Int {
        return jokeList.size
    }


    override fun onBindViewHolder(holder: JokeViewHolder, position: Int) {
        // if scrolled to last element, call onBottomReached
        if (position == jokeList.size - 1){
            onBottomReached()
        }
        val model:JokeView.Model=JokeView.Model(jokeList[position])
        holder.jokeview.setupView(model)
    }

    fun addJoke (joke : Joke ){
        jokeList.add(joke)
        notifyDataSetChanged()
    }



}
