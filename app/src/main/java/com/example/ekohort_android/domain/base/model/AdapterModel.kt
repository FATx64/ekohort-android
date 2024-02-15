package com.example.ekohort_android.domain.base.model

import com.example.ekohort_android.databinding.ItemIbuBinding

abstract class AdapterModel<D> {
    abstract fun bind(binding: ItemIbuBinding, onDelete: (data: D) -> Unit, onUpdate: (data: D) -> Unit, onOpen: (data: D) -> Unit)
    abstract fun compare(other: Any?): Boolean
}