package com.alt.letseatingtime_android.util

object LoginPattern {
    const val id = "^[a-zA-Z]*$"
    const val pw = "^.*(?=^.{8,15}$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$"
    const val name = "^[가-힣]*$"
}