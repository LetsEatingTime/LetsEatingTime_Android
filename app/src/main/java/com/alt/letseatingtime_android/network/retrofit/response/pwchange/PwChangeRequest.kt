package com.alt.letseatingtime_android.network.retrofit.response.pwchange

data class PwChangeRequest(
    val id: String,
    val newPassword: String,
    val password: String
)