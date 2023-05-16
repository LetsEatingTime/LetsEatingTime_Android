package com.alt.letseatingtime_android

import android.content.Context
import android.content.SharedPreferences

class SharedPreferences (context: Context) {
    companion object {
        const val name = "토큰"
    }

    val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(name, Context.MODE_PRIVATE)
//    val editor: SharedPreferences.Editor = sharedPreferences.edit()

    var accessToken: String?
        get() = sharedPreferences.getString("accessToken", "")
        set(value) = sharedPreferences.edit().putString("accessToken", value).apply()
    var refreshToken: String?
        get() = sharedPreferences.getString("refreshToken", "")
        set(value) = sharedPreferences.edit().putString("refreshToken", value).apply()

    var userSchoolNumber: String?
        get() = sharedPreferences.getString("userSchoolNumber", "")
        set(value) = sharedPreferences.edit().putString("userSchoolNumber", value).apply()
    var userPassword: String?
        get() = sharedPreferences.getString("userPassword", "")
        set(value) = sharedPreferences.edit().putString("userPassword", value).apply()
    var userName: String?
        get() = sharedPreferences.getString("userName", "")
        set(value) = sharedPreferences.edit().putString("userName", value).apply()
}