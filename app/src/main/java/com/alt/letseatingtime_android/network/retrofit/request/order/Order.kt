package com.alt.letseatingtime_android.network.retrofit.request.order

import com.google.gson.annotations.SerializedName

data class Order(
    @SerializedName("total")
    val total : Int
)
