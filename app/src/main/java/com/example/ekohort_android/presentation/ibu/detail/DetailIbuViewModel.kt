package com.example.ekohort_android.presentation.ibu.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ekohort_android.domain.Result
import com.example.ekohort_android.domain.ibu.IbuRepository
import com.example.ekohort_android.domain.ibu.model.Ibu
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class DetailIbuViewModel(val repository: IbuRepository, val id: String) : ViewModel() {
    private val _state: MutableStateFlow<Result<Ibu>> = MutableStateFlow(Result.Idle())
    val state: StateFlow<Result<Ibu>> = _state

    init {
        viewModelScope.launch {
            repository.getIbuByIdAsFlow(id).collect {
                _state.value = if (it != null) Result.Success(it) else Result.Error("Ibu with id \"$id\" not found")
            }
        }
    }

    fun delete(data: Ibu) {
        viewModelScope.launch {
            try {
                repository.delete(data.id)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}