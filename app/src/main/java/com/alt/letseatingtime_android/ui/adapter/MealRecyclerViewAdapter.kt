package com.alt.letseatingtime_android.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alt.letseatingtime.databinding.ItemMealBinding
import com.alt.letseatingtime.databinding.ItemMealListBinding
import com.alt.letseatingtime.databinding.ItemRecommendGoodsType1Binding
import com.alt.letseatingtime_android.network.retrofit.response.meal.MealResponse
import com.alt.letseatingtime_android.util.OnSingleClickListener

class MealRecyclerViewAdapter(private val mealList: List<MealResponse>, val date: String) :
    RecyclerView.Adapter<MealRecyclerViewAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemMealListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private var isVisible = false
        fun inputData(data: MealResponse, position: Int) {
            with(binding){
                tvDate.text = date
                tvBreakfast.text = data.data.breakfast.menu.joinToString("",", ","")
                tvLunch.text = data.data.lunch.menu.joinToString("",", ","")
                tvDinner.text = data.data.dinner.menu.joinToString("",", ","")
            }
            binding.llDate.setOnClickListener(OnSingleClickListener{
                if(isVisible){
                    binding.llBreakfast.visibility = View.GONE
                    binding.llLunch.visibility = View.GONE
                    binding.llDinner.visibility = View.GONE
                    binding.icArrowDown.rotation = 180f
                }else{
                    binding.llBreakfast.visibility = View.VISIBLE
                    binding.llLunch.visibility = View.VISIBLE
                    binding.llDinner.visibility = View.VISIBLE
                    binding.icArrowDown.rotation = 0f
                }
                isVisible = !isVisible
            })
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemMealListBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = 1

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.inputData(
            data = mealList[position],
            position = position
        )
    }

}