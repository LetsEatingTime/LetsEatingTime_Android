package com.alt.letseatingtime_android.network.retrofit.response

data class BaseResponse<T>(
    val status : Int,
    val data : T
)
