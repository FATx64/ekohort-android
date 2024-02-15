package com.example.ekohort_android.utils.exts

import android.content.Intent
import android.os.Build
import android.os.Parcelable

inline fun <reified C: Parcelable> Intent.getParcelableExtraCompat(key: String) =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) getParcelableExtra(key, C::class.java)
    else getParcelableExtra(key) as? C