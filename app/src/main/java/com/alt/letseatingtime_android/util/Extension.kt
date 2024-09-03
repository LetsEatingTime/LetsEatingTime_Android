package com.alt.letseatingtime_android.util

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment

fun Fragment.shortToast(message: String) {
    Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
}

fun Fragment.longToast(message: String) {
    Toast.makeText(requireActivity(), message, Toast.LENGTH_LONG).show()
}


internal fun Context.shortToast(text: String) =
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()

internal fun Context.longToast(text: String) =
    Toast.makeText(this, text, Toast.LENGTH_LONG).show()
