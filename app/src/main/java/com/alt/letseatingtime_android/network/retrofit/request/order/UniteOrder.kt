package com.alt.letseatingtime_android.network.retrofit.request.order

import com.google.gson.annotations.SerializedName

data class UniteOrder(
    @SerializedName("order")
    val order : Order,
    @SerializedName("orderDetails")
    val orderDetails : OrderDetails
)
