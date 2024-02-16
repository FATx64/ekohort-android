package com.example.ekohort_android.domain.nakes

import com.example.ekohort_android.domain.nakes.model.Nakes
import kotlinx.coroutines.flow.Flow

interface NakesRepository {
    suspend fun getNakesById(id: String): Nakes?
    fun getNakesByIdAsFlow(id: String): Flow<Nakes?>
    suspend fun getAllNakes(): List<Nakes>
    fun getAllNakesAsFlow(): Flow<List<Nakes>>
    suspend fun insert(data: Nakes)
    suspend fun delete(id: String)
    suspend fun update(id: String, data: Nakes)
}