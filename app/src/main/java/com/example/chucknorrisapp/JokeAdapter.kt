package com.example.chucknorrisapp

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class JokeAdapter() : RecyclerView.Adapter<JokeAdapter.JokeViewHolder>() {

    var jokeList: List<Joke> = emptyList()
        set(jokes) {
            field = jokes
            notifyDataSetChanged()
        }

    class JokeViewHolder(val tview: TextView) : RecyclerView.ViewHolder(tview)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JokeViewHolder {
        // create a new view
        val textView = LayoutInflater.from(parent.context)
            .inflate(R.layout.joke_layout, parent, false) as TextView
        // set the view's size, margins, paddings and layout parameters

        return JokeViewHolder(textView)
    }

    override fun getItemCount(): Int {
        return jokeList.size
    }

    override fun onBindViewHolder(holder: JokeViewHolder, position: Int) {
        holder.tview.text=jokeList[position].value
    }
}
