package com.alt.letseatingtime_android.util

object LoginPattern {
    const val id = "^[a-zA-Z0-9]*$"
    const val pw = "^[a-zA-Z0-9!@#\$%^&*()_+\\-=\\[\\]{}':\",.<>?~/]{7,32}$"
    const val name = "^[가-힣]*$"
    const val grade = "^[1-3]*$"
    const val classname = "^[1-4]*$"
    const val classNo = "^[1-9]*$"
}