package com.example.login.network.retrofit.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("accessToken")
    val accessToken: String,
    @SerializedName("grantType")
    val grantType: String,
    @SerializedName("refreshToken")
    val refreshToken: String
)