package com.alt.letseatingtime_android

import android.app.Application

class MyApplication: Application() {
    companion object {
         lateinit var prefs: SharedPreferences
    }

    override fun onCreate() {
        prefs = SharedPreferences(applicationContext)
        super.onCreate()
    }
}