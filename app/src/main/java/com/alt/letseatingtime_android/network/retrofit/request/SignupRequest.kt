package com.example.login.network.retrofit.request

import com.google.gson.annotations.SerializedName

data class SignupRequest(
    @SerializedName("email")
    val email: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("password")
    val password: String
)
