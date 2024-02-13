package com.example.ekohort_android.domain.base.model

import com.example.ekohort_android.utils.delegates.WriteOnce

open class ModelWithId {
    var id by WriteOnce<String>()
}