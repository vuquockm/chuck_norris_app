
package com.example.chucknorrisapp

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class JokeTouchHelper(
    private val onJokeRemoved: (position: Int)->Unit,
    private val onItemMoved: (start:Int,end: Int)->Boolean
) : ItemTouchHelper(
    object : ItemTouchHelper.SimpleCallback(
        UP or DOWN,
        LEFT or RIGHT
    ) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean{
            val status=onItemMoved(viewHolder.adapterPosition,target.adapterPosition)

            return status
        }





        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
            val position=viewHolder.adapterPosition
            onJokeRemoved(position)
        }


    }
)