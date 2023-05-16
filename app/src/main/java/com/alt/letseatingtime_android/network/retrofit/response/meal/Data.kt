package com.alt.letseatingtime_android.network.retrofit.response.meal

data class Data(
    val breakfast: Breakfast,
    val dinner: Dinner,
    val exists: Boolean,
    val lunch: Lunch
)