package com.alt.letseatingtime_android.ui.adapter.store

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.alt.letseatingtime.databinding.ItemRecommendGoodsType1Binding
import com.alt.letseatingtime.databinding.ItemRecommendGoodsType2Binding
import com.alt.letseatingtime_android.network.retrofit.response.ImageResponse
import com.alt.letseatingtime_android.network.retrofit.response.goods.StoreResponse
import com.alt.letseatingtime_android.ui.viewmodel.StoreViewModel

class StoreGoods1Adapter(private val itemList : List<StoreResponse>, private val imageList : Map<Int, ImageResponse>, private val onClick :  (position : Int)->Unit) : RecyclerView.Adapter<StoreGoods1Adapter.StoreType1ViewHolder>() {
    inner class StoreType1ViewHolder(private val binding: ItemRecommendGoodsType1Binding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bindDate(itemData : StoreResponse, imageUrl : String,position: Int){
                with(binding){
                    tvGoodsName.text = itemData.productName
                    tvPrice.text = itemData.price.toString()
                    ivGoodsImage.load(imageUrl)
                }
                binding.root.setOnClickListener {
                    onClick(position)
                }
                Log.d("StoreGoods1Adapter", "url : $imageUrl")
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreType1ViewHolder {
        val binding = ItemRecommendGoodsType1Binding.inflate(LayoutInflater.from(parent.context),parent,false)

        return StoreType1ViewHolder(binding)
    }

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: StoreType1ViewHolder, position: Int) {
        Log.d("StoreGoods1Adapter", "url : $imageList")
        holder.bindDate(itemData =  itemList[position], position =  position, imageUrl =  imageList[itemList[position].idx]?.fileName ?: "")
    }
}