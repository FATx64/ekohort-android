package com.example.ekohort_android.domain.nakes.model

import android.annotation.SuppressLint
import android.os.Parcelable
import com.example.ekohort_android.databinding.ItemIbuBinding
import com.example.ekohort_android.domain.base.model.AdapterModel
import com.google.firebase.firestore.DocumentId
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class Nakes(
    val name: String = "",
    val nik: Long = 0L,
    val nip: Long = 0L,
    val birthday: Date = Date(),
    val puskesmas: String = "",
    val role: String = "",
    val province: String = "",
    val address: String = "",
    val phoneNumber: String = "",
    @DocumentId
    val id: String = "",
) : AdapterModel<Nakes>(), Parcelable {

    @SuppressLint("SetTextI18n")
    override fun bind(binding: ItemIbuBinding, onDelete: (data: Nakes) -> Unit, onUpdate: (data: Nakes) -> Unit, onOpen: (data: Nakes) -> Unit) {
        binding.tvName.text = "Nama : $name"
        binding.tvNIK.text = "NIK : $nik"
        binding.tvAdditional1.text = "Jabatan : $role"
        binding.tvAdditional2.text = "Alamat : $address"
        binding.btnDelete.setOnClickListener { onDelete(this) }
        binding.btnUpdate.setOnClickListener { onUpdate(this) }
        binding.btnDetail.setOnClickListener { onOpen(this) }
    }

    override fun compare(other: Any?): Boolean {
        if (other !is Nakes) return false
        return id == other.id
    }

    companion object {
        const val COLLECTION_NAME = "nakes"
    }
}