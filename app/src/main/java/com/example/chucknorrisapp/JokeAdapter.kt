package com.example.chucknorrisapp

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class JokeAdapter : RecyclerView.Adapter<JokeAdapter.JokeViewHolder>() {

    var jokes = JokeList.jokes
        set(jokelist) {
            field=jokelist
            notifyDataSetChanged()
        }

    class JokeViewHolder(val t: TextView) : RecyclerView.ViewHolder(t) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JokeViewHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: JokeViewHolder, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}