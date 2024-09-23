package com.alt.letseatingtime_android.ui.adapter.store

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alt.letseatingtime.databinding.ItemRecommendGoodsType2Binding
import com.alt.letseatingtime_android.network.retrofit.response.goods.StoreResponse

class StoreGoods2Adapter(private val itemList : List<StoreResponse>, private val onClick :  (position : Int)->Unit) : RecyclerView.Adapter<StoreGoods2Adapter.StoreType2ViewHolder>() {
    inner class StoreType2ViewHolder(private val binding: ItemRecommendGoodsType2Binding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bindDate(itemData : StoreResponse, position: Int){
                with(binding){
                    tvGoodsName.text = itemData.productName
                    tvPrice.text = itemData.price.toString()
//                    ivGoodsImage.load(itemData.imageUrl)
                }
                binding.root.setOnClickListener {
                    onClick(position)
                }
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreType2ViewHolder {
        val binding = ItemRecommendGoodsType2Binding.inflate(LayoutInflater.from(parent.context),parent,false)

        return StoreType2ViewHolder(binding)
    }

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: StoreType2ViewHolder, position: Int) {
        holder.bindDate(itemList[position], position)
    }
}