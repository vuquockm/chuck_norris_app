package com.example.chucknorrisapp

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.InflateException
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.marginLeft
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.joke_layout.view.*

class JokeView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr){
    var btnstate=R.drawable.ic_star_border_black_24dp


    //val layout:View= LayoutInflater.from(context).inflate(R.layout.joke_layout,null)
    init {
        LayoutInflater.from(context).inflate(R.layout.joke_layout,this,true)



    }


    data class Model(val text:String,val shareIcon:Int,val favoriIcon:Int,val id:String)

    fun setupView(model: Model){
        //inflate(context,R.layout.joke_layout,this)
        btnshare.setOnClickListener{
        Log.d("Gbaman", model.id)
        }
        btnfav.setOnClickListener{
        Log.d("Kemar",model.id)
            btnstate=if(btnstate==R.drawable.ic_star_border_black_24dp)R.drawable.ic_star_black_24dp else R.drawable.ic_star_border_black_24dp
            btnfav.setImageResource(btnstate)

        }
        textview.text=model.text

    }
}