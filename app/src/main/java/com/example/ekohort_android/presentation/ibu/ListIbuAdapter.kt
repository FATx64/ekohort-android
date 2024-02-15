package com.example.ekohort_android.presentation.ibu

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ekohort_android.databinding.ItemIbuBinding
import com.example.ekohort_android.domain.base.model.AdapterModel

class ListIbuAdapter<T, D : AdapterModel<T>>(val data: List<D>, private val onDelete: (T) -> Unit = {}) :
    ListAdapter<D, ListIbuAdapter.ViewHolder<T, D>>(object : DiffUtil.ItemCallback<D>() {
        override fun areContentsTheSame(old: D, new: D): Boolean {
            return old == new
        }

        override fun areItemsTheSame(old: D, new: D): Boolean {
            return old.compare(new)
        }
    }) {
    class ViewHolder<T, D : AdapterModel<T>>(val binding: ItemIbuBinding, private val onDelete: (T) -> Unit) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: D) {
            data.bind(binding, onDelete)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, type: Int): ViewHolder<T, D> {
        val binding: ItemIbuBinding =
            ItemIbuBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, onDelete)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder<T, D>, position: Int) {
        holder.bind(data[position])
    }
}