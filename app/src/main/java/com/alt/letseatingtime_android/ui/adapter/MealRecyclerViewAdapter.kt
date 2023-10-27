package com.alt.letseatingtime_android.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alt.letseatingtime.databinding.ItemMealBinding

class MealRecyclerViewAdapter: RecyclerView.Adapter<MealRecyclerViewAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemMealBinding): RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int = 3

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

}