package com.example.login.network.retrofit.request

import com.google.gson.annotations.SerializedName

data class SignupRequest(
    @SerializedName("name")
    val name: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("schoolNumber")
    val schoolNumber: String,
    @SerializedName("lastTime")
    val lastTime: String,
    @SerializedName("mealApplication")
    val mealApplication: String,
    @SerializedName("userType")
    val userType: Char
)
