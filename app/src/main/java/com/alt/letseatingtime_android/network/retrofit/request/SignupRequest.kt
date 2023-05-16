package com.alt.letseatingtime_android.network.retrofit.request

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
    val grade: Int,
    @SerializedName("className")
    val className: Int,
    @SerializedName("classNo")
    val classNo: Int
)
