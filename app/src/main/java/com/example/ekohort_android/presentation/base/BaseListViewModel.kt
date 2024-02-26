package com.example.ekohort_android.presentation.base

import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

abstract class BaseListViewModel : ViewModel() {
    private val _searchState: MutableStateFlow<String> = MutableStateFlow("")
    val searchState: StateFlow<String> = _searchState

    var job: Job? = null

    abstract fun subscribe()
    @CallSuper
    open fun unsubscribe() {
        job?.cancel()
        job = null
    }

    fun search(keyword: String) {
        viewModelScope.launch {
            _searchState.emit(keyword)
        }
    }
}