package com.example.ekohort_android.utils.exts

import java.text.SimpleDateFormat
import java.util.*

fun Date.toFormattedString(): String {
    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return formatter.format(this)
}