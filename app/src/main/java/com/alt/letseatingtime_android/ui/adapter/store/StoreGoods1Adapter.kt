package com.alt.letseatingtime_android.ui.adapter.store

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.alt.letseatingtime.databinding.ItemRecommendGoodsType1Binding
import com.alt.letseatingtime.databinding.ItemRecommendGoodsType2Binding
import com.alt.letseatingtime_android.network.retrofit.response.goods.StoreResponse

class StoreGoods1Adapter(private val itemList : List<StoreResponse>, private val onClick :  (position : Int)->Unit) : RecyclerView.Adapter<StoreGoods1Adapter.StoreType1ViewHolder>() {
    inner class StoreType1ViewHolder(private val binding: ItemRecommendGoodsType1Binding) :
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreType1ViewHolder {
        val binding = ItemRecommendGoodsType1Binding.inflate(LayoutInflater.from(parent.context),parent,false)

        return StoreType1ViewHolder(binding)
    }

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: StoreType1ViewHolder, position: Int) {
        holder.bindDate(itemList[position], position)
    }
}