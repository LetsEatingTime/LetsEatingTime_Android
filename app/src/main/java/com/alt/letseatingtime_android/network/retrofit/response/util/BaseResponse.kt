package com.alt.letseatingtime_android.network.retrofit.response.util

data class BaseResponse<T>(
    val status :Int,
    val data: T
)