package com.example.chucknorrisapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("sorted", JokeList.jokes.toString())
        rvjokes.layoutManager = LinearLayoutManager(this)
        val adapter=JokeAdapter(JokeList.jokes)
        rvjokes.adapter=adapter


    }

    //page 33 sur le slide








}
