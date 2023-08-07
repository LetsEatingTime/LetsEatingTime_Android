package com.alt.letseatingtime_android.network.retrofit.response.profile

data class User(
    val approvedYn: String,
    val className: Int,
    val classNo: Int,
    val createTime: String,
    val grade: Int,
    val id: String,
    val idx: Int,
    val image: Int,
    val name: String,
    val userType: String,
    val withdrawedTime: Any,
    val withdrawedYn: String
)