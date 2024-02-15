package com.example.ekohort_android.presentation.ibu

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ekohort_android.databinding.ItemIbuBinding
import com.example.ekohort_android.domain.base.model.AdapterModel

class ListIbuAdapter<D : AdapterModel>(val data: List<D>) :
    RecyclerView.Adapter<ListIbuAdapter.ViewHolder<D>>() {
    class ViewHolder<D : AdapterModel>(val binding: ItemIbuBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: D) {
            data.bind(binding)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, type: Int): ViewHolder<D> {
        val binding: ItemIbuBinding =
            ItemIbuBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder<D>, position: Int) {
        holder.bind(data[position])
    }
}