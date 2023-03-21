package com.alt.letseatingtime_android.network.retrofit.response

data class Data(
    val breakfast: String,
    val date: String,
    val dinner: String,
    val exists: Boolean,
    val lunch: String
)