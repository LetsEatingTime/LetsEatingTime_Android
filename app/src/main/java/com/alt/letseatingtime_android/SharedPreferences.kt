package com.alt.letseatingtime_android

import android.content.Context
import android.content.SharedPreferences

class SharedPreferences (context: Context) {
    companion object {
        const val name = "토큰"
    }

    val sharedPreferences: SharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE)
//    val editor: SharedPreferences.Editor = sharedPreferences.edit()

    var accessToken: String?
        get() = sharedPreferences.getString("accessToken", "")
        set(value) = sharedPreferences.edit().putString("accessToken", value).apply()

    var refrashToken: String?
        get() = sharedPreferences.getString("refrashToken", "")
        set(value) = sharedPreferences.edit().putString("refrashToken", value).apply()}