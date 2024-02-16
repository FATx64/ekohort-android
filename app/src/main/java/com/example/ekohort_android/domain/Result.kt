package com.example.ekohort_android.domain

sealed class Result<out T> {
    data class Success<out T>(val data: T? = null) : Result<T>()
    data class Error(val message: String, val exception: Exception? = null) : Result<Nothing>()
    data class Idle<out T>(val firstLoad: Boolean = true) : Result<T>()
}