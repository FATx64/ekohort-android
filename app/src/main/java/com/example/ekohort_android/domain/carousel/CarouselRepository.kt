package com.example.ekohort_android.domain.carousel

interface CarouselRepository {
    suspend fun getAnakCount(): Int
    suspend fun getIbuCount(): Int
    suspend fun getNakesCount(): Int
}