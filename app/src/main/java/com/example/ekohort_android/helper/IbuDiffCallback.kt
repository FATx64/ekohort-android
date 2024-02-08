package com.example.ekohort_android.helper

import androidx.recyclerview.widget.DiffUtil
import com.example.ekohort_android.db.ibuDb

class IbuDiffCallback (private val oldIbuItem: List<ibuDb>, private val newIbuItem: List<ibuDb>) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldIbuItem.size
    override fun getNewListSize(): Int = newIbuItem.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldIbuItem[oldItemPosition].id == newIbuItem[newItemPosition].id
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldIbu = oldIbuItem[oldItemPosition]
        val newIbu = newIbuItem[newItemPosition]
        return oldIbu.name == newIbu.name && oldIbu.nik == newIbu.nik && oldIbu.address == newIbu.address && oldIbu.dayOfBirth == newIbu.dayOfBirth &&
                oldIbu.age == newIbu.age && oldIbu.tb == newIbu.tb && oldIbu.bb == newIbu.bb && oldIbu.bpjs == newIbu.bpjs && oldIbu.tglKunjungan == newIbu.tglKunjungan &&
                oldIbu.tglKunjunganBerikutnya == newIbu.tglKunjunganBerikutnya && oldIbu.contact == newIbu.contact
    }


}