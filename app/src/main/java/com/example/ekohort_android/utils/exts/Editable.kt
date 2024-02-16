package com.example.ekohort_android.utils.exts

import android.text.Editable
import java.text.SimpleDateFormat
import java.util.*

fun Editable?.toDate(defaultValue: Date = Date(0)): Date {
    this ?: return defaultValue
    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return formatter.parse(this.toString()) ?: defaultValue
}

fun Editable?.toLong(defaultValue: Long = 0L): Long {
    return this?.let {
        if (it.isEmpty()) defaultValue else it.toString().toLong()
    } ?: defaultValue
}

fun Editable?.toInt(defaultValue: Int = 0): Int {
    return this?.let {
        if (it.isEmpty()) defaultValue else it.toString().toInt()
    } ?: defaultValue
}