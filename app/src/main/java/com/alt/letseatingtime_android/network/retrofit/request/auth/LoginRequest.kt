package com.alt.letseatingtime_android.network.retrofit.request.auth

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("id")
    val id: String,
    @SerializedName("password")
    val password: String
)