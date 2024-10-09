package com.alt.letseatingtime_android.ui.adapter.meal

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alt.letseatingtime.databinding.ItemMealListBinding
import com.alt.letseatingtime_android.network.retrofit.response.meal.MealResponse

class MealRecyclerViewAdapter(private val mealList: List<MealResponse>, val mealDateList: List<String>) :
    RecyclerView.Adapter<MealRecyclerViewAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemMealListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private var isVisible = false
        fun inputData(data: MealResponse, mealDate : String, position: Int) {
            Log.d("adapter", " date : $mealDate")
            with(binding){
                tvDate.text = "${mealDate.substring(4,6)}월 ${mealDate.substring(6)}일"
                if (data.data.exists){
                    tvBreakfast.text = data.data.breakfast?.menu?.joinToString(", ","","") ?: "정보가 없습니다."
                    tvLunch.text = data.data.lunch?.menu?.joinToString(", ","","") ?: "정보가 없습니다."
                    tvDinner.text = data.data.dinner?.menu?.joinToString(", ","","") ?: "정보가 없습니다."
                }
                else{
                    tvBreakfast.text = "정보가 없습니다."
                    tvLunch.text = "정보가 없습니다."
                    tvDinner.text = "정보가 없습니다."
                }
            }
            binding.llDate.setOnClickListener{
                if(isVisible){
                    binding.llBreakfast.visibility = View.GONE
                    binding.llLunch.visibility = View.GONE
                    binding.llDinner.visibility = View.GONE
                    binding.icArrowDown.rotation = 0f
                }else{
                    binding.llBreakfast.visibility = View.VISIBLE
                    binding.llLunch.visibility = View.VISIBLE
                    binding.llDinner.visibility = View.VISIBLE
                    binding.icArrowDown.rotation = 180f
                }
                isVisible = !isVisible
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemMealListBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = mealList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.inputData(
            data = mealList[position],
            mealDate = mealDateList[position],
            position = position
        )
    }

}