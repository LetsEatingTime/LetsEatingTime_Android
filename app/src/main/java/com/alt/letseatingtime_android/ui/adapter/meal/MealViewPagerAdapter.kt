package com.alt.letseatingtime_android.ui.adapter.meal

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.graphics.drawable.toDrawable
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.alt.letseatingtime.R
import com.alt.letseatingtime.databinding.ItemMealBinding

class MealViewPagerAdapter(val todayMealDateList : List<String>, position: Int = 0) : RecyclerView.Adapter<MealViewPagerAdapter.MealViewHolder>() {
    inner class MealViewHolder(val binding : ItemMealBinding) : RecyclerView.ViewHolder(binding.root){
        fun bindMeal(date : String, position: Int ){
            when(position){
                1->{
                    binding.ivTime.load(R.drawable.lunch)
                }
                2->{
                    binding.ivTime.load(R.drawable.dinner)
                }
                else->{
                    binding.ivTime.load(R.drawable.breakfast)
                }
            }
            binding.tvMenu.text = date
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        val binding = ItemMealBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MealViewHolder(binding)
    }

    override fun getItemCount(): Int  = todayMealDateList.size

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        holder.bindMeal(date = todayMealDateList[position], position)
    }
}