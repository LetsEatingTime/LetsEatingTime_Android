package com.example.login.network.retrofit.request

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("schoolNumber")
    val schoolNumber: String,
    @SerializedName("password")
    val password: String
)