package com.example.ekohort_android.domain.base.model

import com.example.ekohort_android.databinding.ItemIbuBinding

abstract class AdapterModel : ModelWithId() {
    abstract fun bind(binding: ItemIbuBinding)
}