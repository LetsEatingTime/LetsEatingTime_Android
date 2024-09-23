package com.alt.letseatingtime_android.ui.adapter.store.storedata

data class CreateOrderRequest(
    val order: Order,
    val orderDetails: List<OrderDetail>
)