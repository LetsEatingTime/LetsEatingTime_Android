package com.alt.letseatingtime_android.network.retrofit.response.order

import com.alt.letseatingtime_android.network.retrofit.response.goods.StoreResponse

data class OrderListResponse(
    val idx: Int,
    val order: Order,
    val price: Int,
    val product: StoreResponse,
    val quantity: Int
)