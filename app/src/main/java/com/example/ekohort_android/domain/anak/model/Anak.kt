package com.example.ekohort_android.domain.anak.model

import android.annotation.SuppressLint
import android.os.Parcelable
import com.example.ekohort_android.databinding.ItemIbuBinding
import com.example.ekohort_android.domain.base.model.AdapterModel
import com.google.firebase.firestore.DocumentId
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class Anak(
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
) : AdapterModel<Anak>(), Parcelable {
    constructor() : this("", 0L, 0L, Date(), "", "", 0, 0, "", Date(), Date(), "", "")

    @SuppressLint("SetTextI18n")
    override fun bind(binding: ItemIbuBinding, onDelete: (data: Anak) -> Unit, onUpdate: (data: Anak) -> Unit, onOpen: (data: Anak) -> Unit) {
        binding.tvName.text = "Nama : $name"
        binding.tvNIK.text = "NIK : $nik"
        binding.tvAdditional1.text = "No KK : $kk"
        binding.tvAdditional2.text = "Alamat : $address"
        binding.btnDelete.setOnClickListener { onDelete(this) }
        binding.btnUpdate.setOnClickListener { onUpdate(this) }
        binding.btnDetail.setOnClickListener { onOpen(this) }
    }

    override fun compare(other: Any?): Boolean {
        if (other !is Anak) return false
        return id == other.id
    }

    companion object {
        const val COLLECTION_NAME = "ibu"
    }
}