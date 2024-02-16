package com.example.ekohort_android.domain.blog.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BlogModel (

   var title: String,
   var description: String,
   var photo: String,
   var date: String

):Parcelable