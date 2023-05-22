package com.alt.letseatingtime_android.network.retrofit.response.login

data class Data(
    val accessToken: String,
    val grantType: String,
    val refreshToken: String
)