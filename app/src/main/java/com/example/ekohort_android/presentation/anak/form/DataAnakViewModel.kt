package com.example.ekohort_android.presentation.anak.form

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ekohort_android.domain.Result
import com.example.ekohort_android.domain.anak.AnakRepository
import com.example.ekohort_android.domain.anak.model.Anak
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DataAnakViewModel(val repository: AnakRepository) : ViewModel() {
    private val _state: MutableStateFlow<Result<Boolean>> = MutableStateFlow(Result.Idle())
    val state: StateFlow<Result<Boolean>> = _state

    fun insert(data: Anak) {
        viewModelScope.launch {
            _state.emit(Result.Idle(false))
            try {
                repository.insert(data)
            } catch (e: Exception) {
                e.printStackTrace()
                _state.emit(Result.Error("Failed to insert ${data.name}", e))
            }
        }
    }

    fun update(id: String, newData: Anak) {
        viewModelScope.launch {
            _state.emit(Result.Idle(false))
            try {
                repository.update(id, newData)
            } catch (e: Exception) {
                e.printStackTrace()
                _state.emit(Result.Error("Failed to update ${newData.name} ($id)", e))
            }
        }
    }
}