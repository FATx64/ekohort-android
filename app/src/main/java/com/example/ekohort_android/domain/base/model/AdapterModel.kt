package com.example.ekohort_android.domain.base.model

import com.example.ekohort_android.databinding.ItemIbuBinding
import com.example.ekohort_android.domain.ibu.model.Ibu

abstract class AdapterModel<D> {
    abstract fun bind(binding: ItemIbuBinding, onDelete: (data: D) -> Unit, onUpdate: (data: D) -> Unit)
    abstract fun compare(other: Any?): Boolean
}