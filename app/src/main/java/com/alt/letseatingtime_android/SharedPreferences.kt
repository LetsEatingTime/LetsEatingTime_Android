package com.alt.letseatingtime_android

import android.content.Context
import android.content.SharedPreferences

class SharedPreferences(context: Context) {
    companion object {
        const val PREFS_FILENAME = "토큰"
    }

    private val prefs: SharedPreferences =
        context.getSharedPreferences(PREFS_FILENAME, 0)

    var accessToken: String?
        get() = prefs.getString("accessToken", "")
        set(value) = prefs.edit().putString("accessToken", value).apply()
    var refreshToken: String?
        get() = prefs.getString("refreshToken", "")
        set(value) = prefs.edit().putString("refreshToken", value).apply()

    var userID: String?
        get() = prefs.getString("userSchoolNumber", "")
        set(value) = prefs.edit().putString("userSchoolNumber", value).apply()
    var userPassword: String?
        get() = prefs.getString("userPassword", "")
        set(value) = prefs.edit().putString("userPassword", value).apply()
    var userName: String?
        get() = prefs.getString("userName", "")
        set(value) = prefs.edit().putString("userName", value).apply()
}