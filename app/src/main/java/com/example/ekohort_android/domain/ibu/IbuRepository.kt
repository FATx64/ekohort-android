package com.example.ekohort_android.domain.ibu

import com.example.ekohort_android.domain.ibu.model.Ibu

interface IbuRepository {
    suspend fun getAllIbu(): List<Ibu>
    fun insert()
    fun delete()
}