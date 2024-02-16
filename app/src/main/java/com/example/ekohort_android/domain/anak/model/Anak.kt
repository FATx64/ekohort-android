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
    val name: String = "",
    val dadName: String = "",
    val momName: String = "",
    val nik: Long = 0L,
    val gender: Int = LAKI_LAKI,
    val birthday: Date = Date(),
    val address: String = "",
    val weight: Int = 0,
    val description: String = "",
    val visitDate: Date = Date(),
    val nextVisit: Date = Date(),
    val parentContact: String = "",
    @DocumentId
    val id: String = "",
) : AdapterModel<Anak>(), Parcelable {

    @SuppressLint("SetTextI18n")
    override fun bind(binding: ItemIbuBinding, onDelete: (data: Anak) -> Unit, onUpdate: (data: Anak) -> Unit, onOpen: (data: Anak) -> Unit) {
        binding.tvName.text = "Nama : $name"
        binding.tvNIK.text = "NIK : $nik"
        binding.tvAdditional1.text = "Jenis Kelamin : ${gender.gender()}"
        binding.tvAdditional2.text = "Nama Ibu : $momName"
        binding.btnDelete.setOnClickListener { onDelete(this) }
        binding.btnUpdate.setOnClickListener { onUpdate(this) }
        binding.btnDetail.setOnClickListener { onOpen(this) }
    }

    override fun compare(other: Any?): Boolean {
        if (other !is Anak) return false
        return id == other.id
    }

    companion object {
        const val COLLECTION_NAME = "anak"
        const val LAKI_LAKI = 0
        const val PEREMPUAN = 1

        fun Int.gender() =
            if (this == LAKI_LAKI) {
                "Laki-laki"
            } else if (this == PEREMPUAN) {
                "Perempuan"
            } else {
                "Unknown"
            }
    }
}