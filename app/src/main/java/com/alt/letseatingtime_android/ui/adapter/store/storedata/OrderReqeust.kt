package com.alt.letseatingtime_android.ui.adapter.store.storedata

data class OrderRequest(
    val order: OrderTotal,
    val orderDetails: List<OrderDetail>
)

data class OrderTotal(
    val total: Int
)

data class OrderDetail(
    val productId: Int,
    val quantity: Int,
    val price: Int
)
