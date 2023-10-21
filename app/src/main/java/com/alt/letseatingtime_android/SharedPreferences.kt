package com.alt.letseatingtime_android

import android.content.Context
import android.content.SharedPreferences

class SharedPreferences(context: Context) {
    companion object {
        const val PREF_KEY_AUTOLOGIN = "autoLogin"
        const val PREFS_FILENAME = "토큰"
        const val PREF_KEY_USER_ACCESSTOKEN = "accessToken"
        const val PREF_KEY_USER_REFRESHTOKEN = "refreshToken"
    }

    private val prefs: SharedPreferences =
        context.getSharedPreferences(PREFS_FILENAME, 0)

    fun remove(){
        prefs.edit().remove(PREF_KEY_AUTOLOGIN).apply()
        prefs.edit().remove(PREF_KEY_USER_ACCESSTOKEN).apply()
        prefs.edit().remove(PREF_KEY_USER_REFRESHTOKEN).apply()
    }


    var accessToken: String
        get() = prefs.getString(PREF_KEY_USER_ACCESSTOKEN, "").toString()
        set(value) = prefs.edit().putString(PREF_KEY_USER_ACCESSTOKEN, value).apply()
    var refreshToken: String
        get() = prefs.getString(PREF_KEY_USER_REFRESHTOKEN, "").toString()
        set(value) = prefs.edit().putString(PREF_KEY_USER_REFRESHTOKEN, value).apply()

    var autoLogin: Boolean
        get() = prefs.getBoolean(PREF_KEY_AUTOLOGIN,false)
        set(value) = prefs.edit().putBoolean(PREF_KEY_AUTOLOGIN, value).apply()

}