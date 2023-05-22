package com.alt.letseatingtime_android.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alt.letseatingtime_android.ui.adapter.mealdata.MealRecyclerData
import com.example.letseatingtime.databinding.ItemMealRecyclerviewBinding

class MealRecyclerAdapter(var data: List<MealRecyclerData>) : RecyclerView.Adapter<MealRecyclerAdapter.MyViewHolder>() {

    inner class MyViewHolder(private val binding: ItemMealRecyclerviewBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}