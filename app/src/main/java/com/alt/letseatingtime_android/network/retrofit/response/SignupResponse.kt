package com.example.login.network.retrofit.response

import com.google.gson.annotations.SerializedName

//("api/user/signup")
data class SignupResponse(
    @SerializedName("school_number")
    val school_number: String,
    @SerializedName("password")
    val password: String
)