package com.alt.letseatingtime_android.network.retrofit.response.goods

data class StoreResponse(
    val idx: Long,
    val productName: String,
    val price: Int,
    val stock: Int,
    val grantTime: String,
    val image: String?
)
