package com.alt.letseatingtime_android.network.retrofit.response.goods

data class StoreResponse(
    val grantTime: String,
    val idx: Int,
    val price: Int,
    val productName: String,
    val stock: Int
)