package com.example.ekohort_android.domain.ibu.model

import android.annotation.SuppressLint
import com.example.ekohort_android.databinding.ItemIbuBinding
import com.example.ekohort_android.domain.base.model.AdapterModel
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
) : AdapterModel<Ibu>() {
    @SuppressLint("SetTextI18n")
    override fun bind(binding: ItemIbuBinding, onDelete: (data: Ibu) -> Unit) {
        binding.tvName.text = "Nama : $name"
        binding.tvNIK.text = "NIK : $nik"
        binding.tvAdditional1.text = "No KK : $kk"
        binding.tvAdditional2.text = "Alamat : $address"
        binding.btnDelete.setOnClickListener { onDelete(this) }
    }
}