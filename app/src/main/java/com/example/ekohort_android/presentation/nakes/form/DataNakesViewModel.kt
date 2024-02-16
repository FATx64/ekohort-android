package com.example.ekohort_android.presentation.nakes.form

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ekohort_android.domain.Result
import com.example.ekohort_android.domain.nakes.NakesRepository
import com.example.ekohort_android.domain.nakes.model.Nakes
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DataNakesViewModel(val repository: NakesRepository) : ViewModel() {
    private val _state: MutableStateFlow<Result<Boolean>> = MutableStateFlow(Result.Idle())
    val state: StateFlow<Result<Boolean>> = _state

    fun insert(data: Nakes) {
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

    fun update(id: String, newData: Nakes) {
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