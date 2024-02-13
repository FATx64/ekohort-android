package com.example.ekohort_android.domain

sealed class Result<out T> {
    data class Success<out T>(val data: T? = null) : Result<T>()
    data class Error(val message: String? = null) : Result<Nothing>()
    data object Idle : Result<Nothing>()
}