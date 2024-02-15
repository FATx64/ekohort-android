package com.example.ekohort_android.domain.anak

import com.example.ekohort_android.domain.anak.model.Anak
import kotlinx.coroutines.flow.Flow

interface AnakRepository {
    suspend fun getAnakById(id: String): Anak?
    fun getAnakByIdAsFlow(id: String): Flow<Anak?>
    suspend fun getAllAnak(): List<Anak>
    fun getAllAnakAsFlow(): Flow<List<Anak>>
    suspend fun insert(data: Anak)
    suspend fun delete(id: String)
    suspend fun update(id: String, data: Anak)
}