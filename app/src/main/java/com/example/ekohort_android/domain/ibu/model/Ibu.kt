package com.example.ekohort_android.domain.ibu.model

import android.annotation.SuppressLint
import android.os.Parcelable
import com.example.ekohort_android.databinding.ItemIbuBinding
import com.example.ekohort_android.domain.base.model.AdapterModel
import com.google.firebase.firestore.DocumentId
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class Ibu(
    val name: String = "",
    val nik: Long = 0L,
    val kk: Long = 0L,
    val birthday: Date = Date(),
    val province: String = "",
    val address: String = "",
    val height: Int = 0,
    val weight: Int = 0,
    val diagnose: String = "",
    val visitDate: Date = Date(),
    val nextVisit: Date = Date(),
    val phoneNumber: String = "",
    @DocumentId
    val id: String = "",
) : AdapterModel<Ibu>(), Parcelable {

    @SuppressLint("SetTextI18n")
    override fun bind(binding: ItemIbuBinding, onDelete: (data: Ibu) -> Unit, onUpdate: (data: Ibu) -> Unit, onOpen: (data: Ibu) -> Unit) {
        binding.tvName.text = "Nama : $name"
        binding.tvNIK.text = "NIK : $nik"
        binding.tvAdditional1.text = "No KK : $kk"
        binding.tvAdditional2.text = "Alamat : $address"
        binding.btnDelete.setOnClickListener { onDelete(this) }
        binding.btnUpdate.setOnClickListener { onUpdate(this) }
        binding.btnDetail.setOnClickListener { onOpen(this) }
    }

    override fun compare(other: Any?): Boolean {
        if (other !is Ibu) return false
        return id == other.id
    }

    companion object {
        const val COLLECTION_NAME = "ibu"
    }
}