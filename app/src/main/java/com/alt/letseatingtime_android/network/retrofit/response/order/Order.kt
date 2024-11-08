package com.alt.letseatingtime_android.network.retrofit.response.order

data class Order(
    val idx: Int,
    val orderTime: String,
    val total: Int,
    val userId: Int
)