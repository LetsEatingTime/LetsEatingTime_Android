package com.alt.letseatingtime_android

import android.content.Context
import android.content.SharedPreferences

class SharedPreferences(context: Context) {
    companion object {
        const val PREFS_FILENAME = "토큰"
        const val PREF_KEY_USER_ACCESSTOKEN = "accessToken"
        const val PREF_KEY_USER_REFRESHTOKEN = "refreshToken"
        const val PREF_KEY_USER_ID = "userId"
        const val PREF_KEY_USER_IMG = "userImg"
        const val PREF_KEY_USER_PASSWORD = "userPassword"
        const val PREF_KEY_USER_CLASSNAME = "userClassName"
        const val PREF_KEY_USER_CLASSNO = "userClassNo"
        const val PREF_KEY_USER_NAME = "userName"
        const val PREF_KEY_USER_GRADE = "userGrade"
        const val PREF_KEY_MEAL_Breakfast = "mealBreakfast"
        const val PREF_KEY_MEAL_Lunch = "mealLunch"
        const val PREF_KEY_MEAL_Dinner = "mealDinner"
    }

    private val prefs: SharedPreferences =
        context.getSharedPreferences(PREFS_FILENAME, 0)



    var accessToken: String?
        get() = prefs.getString(PREF_KEY_USER_ACCESSTOKEN, "")
        set(value) = prefs.edit().putString(PREF_KEY_USER_ACCESSTOKEN, value).apply()
    var refreshToken: String?
        get() = prefs.getString(PREF_KEY_USER_REFRESHTOKEN, "")
        set(value) = prefs.edit().putString(PREF_KEY_USER_REFRESHTOKEN, value).apply()

    var userID: String?
        get() = prefs.getString(PREF_KEY_USER_ID, "")
        set(value) = prefs.edit().putString(PREF_KEY_USER_ID, value).apply()

    var userGrade: String?
        get() = prefs.getString(PREF_KEY_USER_GRADE, "")
        set(value) = prefs.edit().putString(PREF_KEY_USER_GRADE, value).apply()

    var userPassword: String?
        get() = prefs.getString(PREF_KEY_USER_PASSWORD, "")
        set(value) = prefs.edit().putString(PREF_KEY_USER_PASSWORD, value).apply()

    var userClassName: String?
        get() = prefs.getString(PREF_KEY_USER_CLASSNAME, "")
        set(value) = prefs.edit().putString(PREF_KEY_USER_CLASSNAME, value).apply()

    var userClassNo: String?
        get() = prefs.getString(PREF_KEY_USER_CLASSNO, "")
        set(value) = prefs.edit().putString(PREF_KEY_USER_CLASSNO, value).apply()

    var userName: String?
        get() = prefs.getString(PREF_KEY_USER_NAME, "")
        set(value) = prefs.edit().putString(PREF_KEY_USER_NAME, value).apply()

    var userImg: String?
        get() = prefs.getString(PREF_KEY_USER_IMG, "")
        set(value) = prefs.edit().putString(PREF_KEY_USER_IMG, value).apply()

    var breakfast: String?
        get() = prefs.getString(PREF_KEY_MEAL_Breakfast, "")
        set(value) = prefs.edit().putString(PREF_KEY_MEAL_Breakfast, value).apply()

    var lunch: String?
        get() = prefs.getString(PREF_KEY_MEAL_Lunch, "")
        set(value) = prefs.edit().putString(PREF_KEY_MEAL_Lunch, value).apply()

    var dinner: String?
        get() = prefs.getString(PREF_KEY_MEAL_Dinner, "")
        set(value) = prefs.edit().putString(PREF_KEY_MEAL_Dinner, value).apply()



}