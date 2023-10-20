package com.example.ekohort_android.model

import android.os.Parcelable
import androidx.annotation.StringRes
import kotlinx.parcelize.Parcelize

@Parcelize
data class BlogModel (

   var title: String,
   var description: String,
   var photo: String,
   var date: String

):Parcelable
