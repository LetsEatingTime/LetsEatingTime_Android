package com.alt.letseatingtime_android

import android.app.Application
import android.util.Log

class MyApplication: Application() {
    companion object {
         lateinit var prefs: SharedPreferences
    }

    override fun onCreate() {
        prefs = SharedPreferences(applicationContext)
        super.onCreate()
    }
}