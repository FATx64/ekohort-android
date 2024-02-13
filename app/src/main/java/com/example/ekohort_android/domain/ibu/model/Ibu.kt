package com.example.ekohort_android.domain.ibu.model

import com.example.ekohort_android.domain.base.model.ModelWithId
import java.util.*

data class Ibu(
    val name: String,
    val nik: Long,
    val kk: Long,
    val birthday: Date,
    val province: String,
    val address: String,
    val height: Int,
    val weight: Int,
    val diagnose: String,
    val visitDate: Date,
    val nextVisit: Date,
    val phoneNumber: String,
) : ModelWithId()