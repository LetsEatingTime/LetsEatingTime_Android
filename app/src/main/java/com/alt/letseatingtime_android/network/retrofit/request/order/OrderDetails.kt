package com.alt.letseatingtime_android.network.retrofit.request.order

import com.google.gson.annotations.SerializedName

data class OrderDetails(
    @SerializedName("price")
    val price: Int,
    @SerializedName("productId")
    val productId: Int,
    @SerializedName("quantity")
    val quantity: Int
)