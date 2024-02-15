package com.example.ekohort_android.domain.ibu.model

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Parcelable
import com.example.ekohort_android.databinding.ItemIbuBinding
import com.example.ekohort_android.domain.base.model.AdapterModel
import com.example.ekohort_android.presentation.ibu.form.DataIbuAwalActivity
import com.google.firebase.firestore.DocumentId
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
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
    @DocumentId
    val id: String = "",
) : AdapterModel<Ibu>(), Parcelable {
    constructor() : this("", 0L, 0L, Date(), "", "", 0, 0, "", Date(), Date(), "", "")

    @SuppressLint("SetTextI18n")
    override fun bind(binding: ItemIbuBinding, onDelete: (data: Ibu) -> Unit, onUpdate: (data: Ibu) -> Unit) {
        binding.tvName.text = "Nama : $name"
        binding.tvNIK.text = "NIK : $nik"
        binding.tvAdditional1.text = "No KK : $kk"
        binding.tvAdditional2.text = "Alamat : $address"
        binding.btnDelete.setOnClickListener { onDelete(this) }
        binding.btnUpdate.setOnClickListener { onUpdate(this) }
    }

    override fun compare(other: Any?): Boolean {
        if (other !is Ibu) return false
        return id == other.id
    }

    companion object {
        val collectionName = "ibu"
    }
}