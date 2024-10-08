package com.alt.letseatingtime_android.network.retrofit.response

import com.google.gson.annotations.SerializedName

data class ImageResponse(
    @SerializedName("contentType")
    val contentType: String = "",
    @SerializedName("extension")
    val extension: String = "",
    @SerializedName("fileIdx")
    val fileIdx: Int = -1,
    @SerializedName("fileName")
    val fileName: String = "",
    @SerializedName("originalFileName")
    val originalFileName: String = "",
    @SerializedName("path")
    val path: String = "",
    @SerializedName("registerTime")
    val registerTime: String = ""
)
