package com.example.ekohort_android.domain.ibu

interface IbuRepository {
    suspend fun getAllIbu()
    suspend fun insert()
    suspend fun delete()
}