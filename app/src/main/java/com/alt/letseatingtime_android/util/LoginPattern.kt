package com.alt.letseatingtime_android.util

object LoginPattern {
    const val id = "^[a-zA-Z0-9]*$"
    const val pw = "^.*(?=^.{8,15}$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$"
    const val name = "^[가-힣]*$"
    const val grade = "^[0-9]*$"
}