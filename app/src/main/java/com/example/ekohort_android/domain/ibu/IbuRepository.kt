package com.example.ekohort_android.domain.ibu

import com.example.ekohort_android.domain.ibu.model.Ibu
import kotlinx.coroutines.flow.Flow

interface IbuRepository {
    suspend fun getIbuById(id: String): Ibu?
    fun getIbuByIdAsFlow(id: String): Flow<Ibu?>
    suspend fun getAllIbu(): List<Ibu>
    fun getAllIbuAsFlow(): Flow<List<Ibu>>
    suspend fun insert(data: Ibu)
    suspend fun delete(id: String)
    suspend fun update(id: String, data: Ibu)
}