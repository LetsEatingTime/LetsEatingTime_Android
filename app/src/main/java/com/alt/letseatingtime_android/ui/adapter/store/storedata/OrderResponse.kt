package com.alt.letseatingtime_android.ui.adapter.store.storedata

data class OrderResponse(
    val status: Int,
    val data: OrderData
)

data class OrderData(
    val idx: Int,
    val userId: Int,
    val orderTime: String?,
    val total: Int
)
