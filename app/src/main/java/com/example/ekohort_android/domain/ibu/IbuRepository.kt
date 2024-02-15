package com.example.ekohort_android.domain.ibu

import com.example.ekohort_android.domain.ibu.model.Ibu

interface IbuRepository {
    suspend fun getIbuById(id: String): Ibu?
    suspend fun getAllIbu(): List<Ibu>
    suspend fun insert(data: Ibu)
    suspend fun delete(id: String)
    suspend fun update(id: String, data: Ibu)
}