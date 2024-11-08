package com.alt.letseatingtime_android.network.retrofit.request.auth

import com.google.gson.annotations.SerializedName

data class SignupRequest(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("userType")
    val userType: Char,
    @SerializedName("grade")
    val grade: String,
    @SerializedName("className")
    val className: String,
    @SerializedName("classNo")
    val classNo: String
)
