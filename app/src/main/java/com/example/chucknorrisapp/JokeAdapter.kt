package com.example.chucknorrisapp

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class JokeAdapter(private val jokes: List<Joke>) : RecyclerView.Adapter<JokeAdapter.JokeViewHolder>() {

    /*var jokes = JokeList.jokes
        set(jokelist) {
            field=jokelist
            notifyDataSetChanged()
        }*/

    class JokeViewHolder(val t: TextView) : RecyclerView.ViewHolder(t)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JokeViewHolder {
        // create a new view
        val textView = LayoutInflater.from(parent.context)
            .inflate(R.layout.joke_layout, parent, false) as TextView
        // set the view's size, margins, paddings and layout parameters

        return JokeViewHolder(textView)
    }

    override fun getItemCount(): Int {
        return jokes.size
    }

    override fun onBindViewHolder(holder: JokeViewHolder, position: Int) {
        holder.t.text=jokes[position].value
    }


}