package com.example.ekohort_android.domain.base.model

import com.example.ekohort_android.databinding.ItemIbuBinding

abstract class AdapterModel<D> : ModelWithId() {
    abstract fun bind(binding: ItemIbuBinding, onDelete: (data: D) -> Unit)
}