package com.example.ekohort_android.presentation.anak.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ekohort_android.domain.Result
import com.example.ekohort_android.domain.anak.AnakRepository
import com.example.ekohort_android.domain.anak.model.Anak
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class DetailAnakViewModel(val repository: AnakRepository, val id: String) : ViewModel() {
    private val _state: MutableStateFlow<Result<Anak>> = MutableStateFlow(Result.Idle())
    val state: StateFlow<Result<Anak>> = _state

    init {
        viewModelScope.launch {
            repository.getAnakByIdAsFlow(id).collect {
                _state.value = if (it != null) Result.Success(it) else Result.Error("Anak with id \"$id\" not found")
            }
        }
    }

    fun delete(data: Anak) {
        viewModelScope.launch {
            try {
                repository.delete(data.id)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}